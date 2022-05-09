package com.kuaipin.user.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: ljf
 * @DateTime: 2022/5/7 19:26
 */
@Data
public class UserDTO implements Serializable {

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}
