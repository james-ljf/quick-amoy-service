package com.kuaipin.common.configuration.interceptor;

import cn.hutool.extra.servlet.ServletUtil;
import com.kuaipin.common.annotation.AccessLimit;
import com.kuaipin.common.exception.BizBusinessException;
import com.kuaipin.common.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 接口限流拦截器
 * @Author: ljf
 * @DateTime: 2022/4/5 14:54
 */
@Slf4j
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler){
        if (handler instanceof HandlerMethod){
            log.info("进入接口限流器........");
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (!method.isAnnotationPresent(AccessLimit.class)){
                return true;
            }
            AccessLimit accessLimit = method.getAnnotation(AccessLimit.class);
            if (ObjectUtils.isEmpty(accessLimit)){
                return true;
            }
            // 访问次数限制
            int limit = accessLimit.limit();
            // 指定时间段
            int sec = accessLimit.sec();
            // 获取redis的key
            String key = ServletUtil.getClientIP(request, (String) null) + request.getRequestURI();
            // 获取redis中某时间段已经访问的次数
            Integer maxLimit = (Integer) RedisUtil.get(key);
            if (ObjectUtils.isEmpty(maxLimit)){
                RedisUtil.set(key, 1, sec, TimeUnit.SECONDS);
            }else if (maxLimit < limit){
                // 已经访问的次数还没达到限制次数
                RedisUtil.set(key, maxLimit + 1, sec, TimeUnit.SECONDS);
            }else {
                throw new BizBusinessException("请求频繁，请稍后再试。");
            }
            log.info("当前接口方法:{}，在{}时间段内已请求了{}次", method, sec, maxLimit);
        }
        return true;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) {
        // TODO document why this method is empty
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
        // TODO document why this method is empty
    }
}
