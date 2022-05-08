package com.kuaipin.user.server.entity.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @Author: ljf
 * @DateTime: 2022/4/29 15:13
 */
@Data
public class UserVO implements Serializable {

    /**
     * 用户id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long uId;

    /**
     * 邮箱（账号）
     */
    private String uEmail;


    /**
     * 昵称
     */
    private String uNickname;

    /**
     * 性别
     */
    private String uSex;

    @Override
    public String toString() {
        return new StringJoiner(", ", UserVO.class.getSimpleName() + "[", "]")
                .add("uId=" + uId)
                .add("uEmail='" + uEmail + "'")
                .add("uNickname='" + uNickname + "'")
                .add("uSex='" + uSex + "'")
                .toString();
    }
}
