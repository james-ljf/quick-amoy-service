package com.kuaipin.search.server.entity.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: ljf
 * @DateTime: 2021/12/18 16:30
 */
@Data
@Accessors(chain = true)
public class BusinessInfo implements Serializable {

    private Long id;

    /**
     * 商家id
     */
    private Long businessId;

    /**
     * 商家名称
     */
    private String businessName;

    /**
     * 是否已认证（1 已认证， 0 未认证）
     */
    private Integer isAuthentication;

    /**
     * 是否是旗舰店（1 是 ，0 不是）
     */
    private Integer isFlagship;

    private Date createTime;

    private Date updateTime;

}
