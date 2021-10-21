package com.game.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 供调用的接口注解
 * @author lijf
 */
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CallableAPI {

    @Description(value = "接口描述")
    String desc() default "";

    @Description(value = "底层接口路径")
    String path() default "";
}
