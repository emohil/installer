package com.company.api.fw.service.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.company.api.fw.DictCodes;
import com.company.api.fw.TransformField;
import com.company.api.fw.service.SysDictService;
import com.company.util.New;

@Service
public class TransformFieldExecutor {

    private static final Logger log = LoggerFactory.getLogger(TransformFieldExecutor.class);

    public static final String DEFAULT_SUFFIX = "Disp";
    
    @Autowired
    private SysDictService sysDictService;


    private ConcurrentHashMap<String, Iterable<TransformJob>> rwsCache = New.concurrentMap();

    private static class TransformJob {
        public final String code;
        public final boolean mutiple;
        public final PropertyDescriptor src, dest;

        public TransformJob(String code, boolean mutiple, PropertyDescriptor src, PropertyDescriptor dest) {
            super();
            this.code = code;
            this.mutiple = mutiple;
            this.src = src;
            this.dest = dest;
        }
    }

    public void transforms(Iterable<? extends Object> beans) {
        for (Object bean : beans) {
            transform(bean);
        }
    }

    public void transform(Object bean) {
        Iterable<TransformJob> rws = getRws(bean.getClass());
        for (TransformJob rw : rws) {
            Object ori;
            try {
                ori = rw.src.getReadMethod().invoke(bean);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            Object dest;
            try {
                String gcode = rw.code;
                if (StringUtils.isEmpty(ori)) {
                    dest = null;
                } else if (gcode.startsWith(DictCodes.DF_PROTOCOL)) {
                    String format = gcode.substring(DictCodes.DF_PROTOCOL.length());
                    dest = new SimpleDateFormat(format).format(ori);
                } else {
                    if (ori instanceof Boolean) {
                        ori = ori.toString().toUpperCase(); // TODO: TRUE('是')...
                    }
                    if (!rw.mutiple) {
                        dest = sysDictService.text(gcode, String.valueOf(ori));
                    } else {
                        dest = sysDictService.texts(gcode, String.valueOf(ori).split(","));
                    }
                }
            } catch (Throwable tr) {
                log.error("Cannot calculate value on group code " + rw.code + " with type " + type(ori)
                        + ". Original value will be used as default.", tr);
                dest = null;
            }

            // fix the destination value as original if NULL. 
            if (dest == null)
                dest = ori;

            try {
                rw.dest.getWriteMethod().invoke(bean, dest);
            } catch (Exception e) {
                log.error("Fail to set {} with type {}.", rw.dest.getWriteMethod(), type(dest));
                throw new RuntimeException(e);
            }
        }
    }

    private static final String type(Object o) {
        return o == null ? "Null" : o.getClass().getName();
    }

    private Iterable<TransformJob> getRws(Class<?> c1) {
        String cname = c1.getName();

        Iterable<TransformJob> rws = rwsCache.get(cname);
        if (rws == null) {
            rws = buildRws(c1);
            rwsCache.putIfAbsent(cname, rws);
        }

        return rws;
    }

    private Iterable<TransformJob> buildRws(Class<? extends Object> class1) {

        log.debug("Parsing transform model for class {}.", class1.getName());

        LinkedHashSet<TransformJob> ret = New.linkedSet();
        for (Class<?> c = class1; c != Object.class; c = c.getSuperclass()) {
            for (Field f : c.getDeclaredFields()) {
                if (Modifier.isStatic(f.getModifiers()))
                    continue;

                TransformField anno = f.getAnnotation(TransformField.class);
                if (anno == null)
                    continue;

                String srcName = f.getName();
                String targetName = anno.targetField();
                if (targetName == null || targetName.isEmpty())
                    targetName = srcName + DEFAULT_SUFFIX;

                String gcode = anno.groupCode();
                if (gcode == null || gcode.isEmpty())
                    throw new IllegalArgumentException("GroupCode cannot be null/empty." + c.getName() + "#" + srcName);
                boolean mutiple = anno.mutiple();

                PropertyDescriptor srcPd = BeanUtils.getPropertyDescriptor(c, srcName);
                PropertyDescriptor destPd = BeanUtils.getPropertyDescriptor(c, targetName);
                if (destPd == null)
                    throw new IllegalArgumentException("Destination property not found: " + c.getClass().getName() + "." + targetName);

                log.debug("In class {}, property {} will be transformed to {} with group code {}.", //
                        c.getName(), srcName, targetName, gcode);

                ret.add(new TransformJob(gcode, mutiple, srcPd, destPd));
            }
        }

        log.debug("Finish parsing transform model for class {}.", class1.getName());

        return ret;
    }

}
