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
    SEARCH_ERROR("4004", "搜索出现错误"),
    // 验证码已存在
    VERIFY_IN("5001", "请不要频繁发送验证码"),
    // 验证码错误
    VERIFY_ERROR("5002", "验证码错误"),
    // 用户已注册
    USER_REGISTERED("5010", "该用户已注册"),
    // 用户注册失败
    USER_REGISTER_ERROR("5011", "注册失败，请联系客服"),
    // 密码错误
    PWD_ERROR("5020", "密码错误"),
    // 数据库操作出错
    DATABASE_ERROR("4050", "操作出现错误");

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
