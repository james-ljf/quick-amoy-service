package com.kuaipin.user.server;

import com.kuaipin.common.annotation.StarterApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: ljf
 * @DateTime: 2022/3/22 14:20
 */
@StarterApplication
@EnableDiscoveryClient
@MapperScan(value = {"com.kuaipin.user.server.repository.impl.mapper"})
public class UserApplication {

    private static final Logger logger = LoggerFactory.getLogger(UserApplication.class);

    public static void main(String[] args){
        SpringApplication.run(UserApplication.class, args);
        logger.info("user-web 服务成功启动......");
    }

}
