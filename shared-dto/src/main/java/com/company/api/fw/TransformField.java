package com.company.api.fw;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TransformField {

    /**
     * 字典的分组代码 
     * 
     * @return
     */
    String groupCode();

    /**
     * 默认值是原来的fieldName + Disp
     * 
     * @return
     */
    String targetField() default "";
    
    boolean mutiple() default false;

}
