package com.game.user.web;

import com.game.common.annotation.StarterApplication;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ForkJoinPool;

/**
 * 用户服务启动类
 * @author lijf
 */
@StarterApplication
@MapperScan(basePackages = "com.game.user.web.repository.impl.mapper")
@Slf4j
public class UserServiceApplication {
    public static void main(String[] args){
        log.info("getParallelism=" + ForkJoinPool.commonPool().getParallelism());
        SpringApplication.run(UserServiceApplication.class);
        log.info(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + "user service started!");
    }
}
