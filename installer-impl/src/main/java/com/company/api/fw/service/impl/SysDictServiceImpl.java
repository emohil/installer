package com.company.api.fw.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.company.api.dict.service.DictService;
import com.company.api.fw.DictEntry;
import com.company.api.fw.EnumDict;
import com.company.api.fw.service.SysDictService;
import com.company.po.dict.Dict;
import com.company.dto.Order;
import com.company.dto.SysDict;
import com.company.util.New;
import com.company.util.NumberUtil;

@Service(SysDictService.BEAN_NAME)
public class SysDictServiceImpl implements SysDictService,
        ApplicationListener<ContextRefreshedEvent> {
    
    private static final Logger log = LoggerFactory.getLogger(SysDictServiceImpl.class);
    
    @Autowired
    private DictService dictService;
    
    private Map<String, List<SysDict>> cacheMap = null;
    
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.refreshDict();
    }

    
    @Override
    public void refreshDict() {
        cacheMap = mockData();
    }
    
    
    private Map<String, List<SysDict>> mockData() {
        Map<String, List<SysDict>> ret = New.linkedMap();

        log.debug("Looking up for dictionary in db.");
        List<Dict> _list = dictService.findList(null, null, null, Arrays.asList(Order.asc("orders")));
        if (_list != null && _list.size() > 0) {
            String _grCode = null;
            SysDict SysDict = null;
            for (Dict _vo : _list) {
                _grCode = _vo.getGroupCode();
                log.debug("Add group of {}.", _grCode);
                SysDict = new SysDict(_vo.getValue1().trim(), _vo.getText1().trim(), NumberUtil.toInt(_vo.getOrders()));
                log.debug("  Add entry {} -> {}.", _vo.getValue1(), _vo.getText1());
                if (ret.get(_grCode) != null) {
                    ret.get(_grCode).add(SysDict);
                } else {
                    List<SysDict> _addList = New.list();
                    _addList.add(SysDict);
                    ret.put(_grCode, _addList);
                }
            }
        }

        log.debug("Scan for Enum classes for dictionary. {}", "com.company");
        Reflections reflections = new Reflections("com.company");
        Set<Class<?>> cs = reflections.getTypesAnnotatedWith(EnumDict.class);
        for (Class<?> c : cs) {
            if (!(Enum.class.isAssignableFrom(c)))
                throw new IllegalArgumentException("Not an enum type: " + c.getName());

            EnumDict anno = c.getAnnotation(EnumDict.class);
            String gcode = anno.value();
            if (gcode == null || gcode.isEmpty())
                gcode = c.getName();
            log.debug("Add group {}.", gcode);

            @SuppressWarnings("unchecked")
            Class<? extends DictEntry> xc = (Class<? extends DictEntry>) c;
            List<SysDict> dicts = New.list();
            ret.put(gcode, dicts);
            int orders = 0;
            for (DictEntry e : xc.getEnumConstants()) {
                SysDict d = new SysDict(e.name(), e.text(), orders++);
                log.debug("  Add entry {} -> {}.", e.name(), e.text());
                dicts.add(d);
            }
        }

        return ret;
    }
    
    
    private List<SysDict> listGroup(String groupCode) {
        if (cacheMap == null || cacheMap.isEmpty()) {
            refreshDict();
        }
        List<SysDict> ret = cacheMap.get(groupCode);
        if (ret == null) {
            throw new IllegalStateException("No dictionary found for groupCode: " + groupCode);
        }
        return ret;
    }
    


    @Override
    public List<SysDict> listGroupCopy(String groupCode, SysDict top, String... excludeKeys) {

        List<SysDict> ret = New.list();
        if (top != null) {
            ret.add(0, top);
        }

        List<SysDict> origItems = listGroup(groupCode);

        if (excludeKeys.length == 0) {
            ret.addAll(origItems);
        } else {
            List<String> excludeKeyList = New.list(excludeKeys);

            for (SysDict origItem : origItems) {
                if (excludeKeyList.contains(origItem.getValue())) {
                    continue;
                }
                ret.add(origItem);
            }
        }
        return ret;
    }

    @Override
    public List<SysDict> listGroupCopy(String groupCode, String... excludeKeys) {
        return listGroupCopy(groupCode, null, excludeKeys);
    }
    

    @Override
    public List<SysDict> listGroupTransfer(String groupCode, SysDict top, String... includeKeys) {
        
        List<SysDict> ret = New.list();
        if (top != null) {
            ret.add(0, top);
        }
        
        if (includeKeys.length > 0) {
            List<String> includeKeyList = New.list(includeKeys);

            List<SysDict> origItems = listGroup(groupCode);
            for (SysDict origItem : origItems) {
                if (includeKeyList.contains(origItem.getValue())) {
                    ret.add(origItem);
                }
            }
        }
        
        return ret;
    }


    @Override
    public List<SysDict> listGroupTransfer(String groupCode, String... includeKeys) {
        return listGroupTransfer(groupCode, null, includeKeys);
    }


    @Override
    public String text(String groupCode, String value) {
        return findText(listGroup(groupCode), value);
    }

    @Override
    public String texts(String groupCode, String... values) {
        return findTexts(listGroup(groupCode), values);
    }

    
    
    private static String findText(List<SysDict> dicts, String key) {
        String _dictKey = null;
        for (SysDict _dict : dicts) {
            _dictKey = _dict.getValue();
            if (_dictKey != null && _dictKey.equals(key)) {
                return _dict.getText();
            }
        }
        return null;
    }

    private static String findTexts(List<SysDict> dicts, String[] keys) {
        StringBuilder sbd = new StringBuilder();
        for (String _tmpKey : keys) {
            if (_tmpKey != null) {
                for (SysDict _dict : dicts) {
                    if (_tmpKey.equals(_dict.getValue())) {
                        sbd.append(_dict.getText()).append(",");
                    }
                }
            }
        }
        if (sbd.lastIndexOf(",") > -1) {
            sbd.deleteCharAt(sbd.lastIndexOf(","));
        }
        return sbd.toString();
    }
    
    public Integer order(String groupCode, String value) {
        for (SysDict _dict : listGroup(groupCode)) {
            String _dictKey = _dict.getValue();
            if (_dictKey != null && _dictKey.equals(value)) {
                return _dict.getOrders();
            }
        }
        return null;
    }
}