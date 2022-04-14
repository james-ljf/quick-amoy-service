package com.kuaipin.common.configuration;

import org.apache.dubbo.config.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: ljf
 * @DateTime: 2022/4/8 21:15
 */
@Configuration
public class DubboConfig {
    /**
     * 消费者配置不主动监督生产者服务
     */
    @Bean
    public ConsumerConfig setConsumerConfig(){
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setCheck(false);
        consumerConfig.setTimeout(40000);
        return consumerConfig;
    }
}
