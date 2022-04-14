package com.kuaipin.common.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @Author: ljf
 * @DateTime: 2022/3/24 13:10
 */
@Slf4j
@Aspect
@Component
public class SystemTimeAspectj {

    @Pointcut("@annotation(com.kuaipin.common.annotation.SystemTime) || @within(com.kuaipin.common.annotation.SystemTime)")
    public void currentTimeLog() {
        // TODO
    }

    @Around("currentTimeLog()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //开始时间
        long start = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        long end = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) point.getSignature();

        //请求的方法名
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.debug("【接口执行时间】接口名：{}.{},执行时间:{}毫秒",className, methodName,(end-start));
        log.info("【接口执行时间】接口名：{}.{},执行时间:{}毫秒",className, methodName,(end-start));
        return result;
    }

}
