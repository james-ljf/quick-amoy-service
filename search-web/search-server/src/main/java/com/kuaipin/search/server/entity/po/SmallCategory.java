package com.kuaipin.search.server.entity.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品品类分类
 * @Author: ljf
 * @DateTime: 2021/12/18 13:05
 */
@Data
@Accessors(chain = true)
public class SmallCategory implements Serializable {

    private Long id;

    /**
     * 商品小品类id
     */
    private Long sTypeId;

    /**
     * 商品小品类名称
     */
    private String sTypeName;

    /**
     * 商品大品类id
     */
    private Long bTypeId;

    private Date createTime;

    private Date updateTime;

}
