package com.kuaipin.user.server;

import com.kuaipin.common.annotation.StarterApplication;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: lijf
 * @DateTime: 2022/3/22 14:20
 */
@StarterApplication
@EnableDiscoveryClient
@EnableDubbo
@MapperScan(value = {"com.kuaipin.search.server.repository.impl.mapper", "com.kuaipin.search.server.mapper"})
public class UserApplication {

    private static final Logger logger = LoggerFactory.getLogger(UserApplication.class);

    public static void main(String[] args){
        SpringApplication.run(UserApplication.class, args);
        logger.info("user-web 服务成功启动......");
    }

}