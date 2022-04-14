package com.kuaipin.user.server.entity.request;

import lombok.Data;


/**
 * @Author: ljf
 * @DateTime: 2022/4/13 23:48
 */
@Data
public class UserRequest {

    /**
     * 用户id
     */
    private Long uId;

    /**
     * 邮箱（账号）
     */
    private String uEmail;

    /**
     * 密码
     */
    private String uPassword;

    /**
     * 昵称
     */
    private String uNickname;

    /**
     * 性别
     */
    private String uSex;

    /**
     * 验证码
     */
    private String verify;

    @Override
    public String toString() {
        return "UserRequest{" +
                "uId=" + uId +
                ", uEmail='" + uEmail + '\'' +
                ", uPassword='" + uPassword + '\'' +
                ", uNickname='" + uNickname + '\'' +
                ", uSex='" + uSex + '\'' +
                ", verify='" + verify + '\'' +
                '}';
    }
}
