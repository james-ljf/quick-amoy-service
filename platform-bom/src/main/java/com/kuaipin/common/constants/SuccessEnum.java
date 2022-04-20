package com.kuaipin.common.constants;

/**
 * @Author: ljf
 * @DateTime: 2022/4/12 15:27
 */
public enum SuccessEnum {

    // 数据库操作成功
    OPERATION_SUCCESS("操作成功"),
    // 发送验证码成功
    VERIFY_SUCCESS("发送成功");

    private final String msg;

    SuccessEnum(String msg) {
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }

}
