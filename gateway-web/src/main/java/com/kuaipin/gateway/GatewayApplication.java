package com.kuaipin.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: ljf
 * @DateTime: 2022/4/13 10:00
 */
@SpringBootApplication
public class GatewayApplication {

    private static final Logger logger = LoggerFactory.getLogger(GatewayApplication.class);

    public static void main(String[] args){
        SpringApplication.run(GatewayApplication.class, args);
        logger.info("gateway-web 网关服务成功启动......");
    }

}
