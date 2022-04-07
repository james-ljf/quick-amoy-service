package com.kuaipin.user.server.entity.po;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 * @Author: ljf
 * @DateTime: 2021/12/13 14:03
 */
@Data
@Accessors(chain = true)
public class User implements Serializable {

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}
