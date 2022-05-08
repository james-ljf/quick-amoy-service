package com.kuaipin.admin.entity.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.StringJoiner;

/**
 * @Author: ljf
 * @DateTime: 2022/5/4 13:23
 */
@Data
public class AdminVO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long adminId;

    private String token;

    private String adminAccount;

    @Override
    public String toString() {
        return new StringJoiner(", ", AdminVO.class.getSimpleName() + "[", "]")
                .add("adminId=" + adminId)
                .add("token='" + token + "'")
                .add("adminAccount='" + adminAccount + "'")
                .toString();
    }
}
