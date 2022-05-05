package com.kuaipin.user.server.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

/**
 * 用户信息
 * @Author: ljf
 * @DateTime: 2021/12/13 14:03
 */
@Data
public class User implements Serializable {

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

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("uId=" + uId)
                .add("uEmail='" + uEmail + "'")
                .add("uPassword='" + uPassword + "'")
                .add("uNickname='" + uNickname + "'")
                .add("uSex='" + uSex + "'")
                .add("createTime=" + createTime)
                .add("updateTime=" + updateTime)
                .toString();
    }
}
