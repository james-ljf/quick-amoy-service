package com.kuaipin.gateway.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 获取path.properties配置文件内容
 * @Author: ljf
 * @DateTime: 2022/4/29 14:11
 */
@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "filter", ignoreUnknownFields = false)
@PropertySource("classpath:path.properties")
public class LoginPathConfig {

    private String[] path;

}
