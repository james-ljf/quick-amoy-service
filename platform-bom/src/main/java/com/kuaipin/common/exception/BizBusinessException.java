package com.kuaipin.common.exception;

/**
 * 自定义异常
 * @Author: ljf
 * @DateTime: 2022/4/5 15:27
 */
public class BizBusinessException extends RuntimeException {

    public BizBusinessException(){}
    public BizBusinessException(String exceptionMsg){
        super(exceptionMsg);
    }

}
