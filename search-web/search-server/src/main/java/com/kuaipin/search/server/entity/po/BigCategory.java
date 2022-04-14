package com.kuaipin.search.server.entity.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: ljf
 * @DateTime: 2021/12/18 14:07
 */
@Data
@Accessors(chain = true)
public class BigCategory implements Serializable {

    private Long id;

    /**
     * 商品大品类id
     */
    private Long bTypeId;

    /**
     * 商品小品类名称
     */
    private String sTypeName;

    private Date createTime;

    private Date updateTime;

}
