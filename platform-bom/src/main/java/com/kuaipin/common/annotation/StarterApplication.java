package com.kuaipin.common.annotation;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
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
@EnableDiscoveryClient
@ComponentScan(value = "com.kuaipin")
public @interface StarterApplication {
}
