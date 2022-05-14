package com.kuaipin.common.annotation;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

/**
 * 自定义启动类注解
 * @author lijf
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SpringBootApplication
@ComponentScan(value = "com.kuaipin")
public @interface StarterApplication {
}
