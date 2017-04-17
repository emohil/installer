package com.company.api.fw;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用此标记的枚举类型会被系统自动记录为字典 
 * 
 * @author lihome
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumDict {
    
    String value();

}
