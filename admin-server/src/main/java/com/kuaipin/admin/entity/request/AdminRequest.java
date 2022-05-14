package com.kuaipin.admin.entity.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ljf
 * @DateTime: 2022/5/4 13:26
 */
@Data
public class AdminRequest implements Serializable {

    private String adminAccount;

    private String adminPwd;

}
