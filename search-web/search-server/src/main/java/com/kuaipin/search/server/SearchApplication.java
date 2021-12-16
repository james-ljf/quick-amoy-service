package com.kuaipin.search.server;

import com.kuaipin.common.annotation.StarterApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 搜索服务启动入口
 * @Author: ljf
 * @DateTime: 2021/12/15 23:10
 */
@StarterApplication
@EnableDiscoveryClient
@MapperScan(value = "com.kuaipin.search.server.repository.impl.mapper")
public class SearchApplication {

    public static void main(String[] args){
        SpringApplication.run(SearchApplication.class, args);
    }

}
