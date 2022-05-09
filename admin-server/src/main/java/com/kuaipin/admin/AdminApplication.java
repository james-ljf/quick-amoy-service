package com.kuaipin.admin;

import com.kuaipin.common.annotation.StarterApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: ljf
 * @DateTime: 2022/5/9 16:27
 */
@StarterApplication
@EnableDiscoveryClient
@MapperScan(value = {"com.kuaipin.admin.mapper"})
public class AdminApplication {

    private static final Logger log = LoggerFactory.getLogger(AdminApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
        log.info("admin-server服务启动成功......");
    }


}
