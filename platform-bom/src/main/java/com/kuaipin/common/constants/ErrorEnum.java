package com.kuaipin.common.constants;

/**
 * 错误信息枚举
 * @Author: ljf
 * @DateTime: 2022/3/24 15:35
 */
public enum ErrorEnum {
    // 线程任务执行出现异常
    EXECUTE_ERROR("4001", "线程任务执行出现异常，结果无法获取。"),
    // 搜索出错
    SEARCH_ERROR("4004", "搜索出现错误");

    private final String code;
    private final String msg;

    ErrorEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public String getCode(){
        return this.code;
    }
    public String getMsg(){
        return this.msg;
    }
}
