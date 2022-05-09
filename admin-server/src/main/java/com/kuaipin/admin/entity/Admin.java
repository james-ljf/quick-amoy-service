package com.kuaipin.admin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ljf
 * @DateTime: 2022/5/3 21:49
 */
@Data
public class Admin implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long adminId;

    private String adminAccount;

    private String adminPwd;

    private String createTime;

    private String updateTime;

}
