//package com.kuaipin.search.server.configuration;
//
//import com.kuaipin.common.configuration.interceptor.AccessLimitInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * 跨域配置
// * @Author: ljf
// * @DateTime: 2022/3/22 14:42
// */
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Autowired
//    private AccessLimitInterceptor accessLimitInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(accessLimitInterceptor);
//    }
//}
