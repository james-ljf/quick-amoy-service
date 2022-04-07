package com.kuaipin.common.exception.handler;

import com.kuaipin.common.entity.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 * @Author: ljf
 * @DateTime: 2022/4/5 15:29
 */
@RestControllerAdvice
public class BizBusinessExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(BizBusinessExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Response<Object> errorHandler(Exception e){
        log.error("[40010.an exception] : msg = {}", e.getMessage());
        return Response.fail("40010", e.toString());
    }

}
