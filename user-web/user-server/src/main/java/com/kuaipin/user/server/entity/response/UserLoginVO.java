package com.kuaipin.user.server.entity.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ljf
 * @DateTime: 2022/4/13 13:54
 */
@Data
public class UserLoginVO implements Serializable {

    private Long uId;

    private String token;

    @Override
    public String toString() {
        return "UserLoginVO{" +
                "uId=" + uId +
                ", token='" + token + '\'' +
                '}';
    }
}
