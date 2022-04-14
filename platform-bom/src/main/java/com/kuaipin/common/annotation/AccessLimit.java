package com.kuaipin.common.annotation;

import java.lang.annotation.*;

/**
 * 接口限流注解
 * @Author: ljf
 * @DateTime: 2022/4/5 14:51
 */
@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {

    // 指定sec时间段内的访问次数限制
    int limit() default 6;

    // 时间段,默认单位：秒
    int sec() default 5;
}
