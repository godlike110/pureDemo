package com.waimai.ops.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 属性通过注解传值
 * 
 * @author zhiwei.wen
 * @time 2015年4月28日下午12:18:09
 */
@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Config {
	String value() default "";
}
