package com.kuaipin.search.server;

import com.kuaipin.common.annotation.StarterApplication;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 搜索服务启动入口
 * @Author: ljf
 * @DateTime: 2021/12/15 23:10
 */
@StarterApplication
@EnableDubbo
@EnableDiscoveryClient
@MapperScan(value = {"com.kuaipin.search.server.mapper"})
public class SearchApplication {

    private static final Logger logger = LoggerFactory.getLogger(SearchApplication.class);

    public static void main(String[] args){
        SpringApplication.run(SearchApplication.class, args);
        logger.info("search-web 服务成功启动......");
    }

}
