package com.kuaipin.user.server.entity.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @Author: ljf
 * @DateTime: 2022/4/13 13:54
 */
@Data
public class UserLoginVO implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long uId;

    private String token;

    private String nickName;

    @Override
    public String toString() {
        return new StringJoiner(", ", UserLoginVO.class.getSimpleName() + "[", "]")
                .add("uId=" + uId)
                .add("token='" + token + "'")
                .add("nickName='" + nickName + "'")
                .toString();
    }
}
