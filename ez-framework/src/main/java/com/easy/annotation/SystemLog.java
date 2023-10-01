package com.easy.annotation;

import org.aspectj.lang.annotation.Around;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wxx
 * @create 08--25--10:45
 * @description:
 */

// @Around()
@Retention(RetentionPolicy.RUNTIME)//表示mySystemlog注解类会保持到runtime阶段
@Target({ElementType.METHOD})//表示mySystemlog注解类的注解功能只能用于方法上
//'自定义注解'类，以后就可以使用@mySystemlog注解了，注解名就是下面那行的接口名
public @interface SystemLog {
    //为controller提供接口的描述信息，用于'日志记录'功能
    String BusinessName();

}
