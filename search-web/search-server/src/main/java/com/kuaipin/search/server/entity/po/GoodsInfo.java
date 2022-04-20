package com.kuaipin.search.server.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: ljf
 * @DateTime: 2021/12/18 14:08
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfo implements Serializable {

    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 编号（对外暴露）
     */
    private Long goodsNumber;
    /**
     * 名称
     */
    private String goodsName;
    /**
     * 品牌
     */
    private String goodsBrand;
    /**
     * 品类id
     */
    private Long sTypeId;
    /**
     * 图片地址
     */
    private String goodsPic;
    /**
     * 规格/版本
     */
    private String goodsEdition;
    /**
     * 商家id
     */
    private Long businessId;
    /**
     * 评论数量
     */
    private Integer goodsComment;
    /**
     * 价格
     */
    private String goodsPrice;

    private Date createTime;

    private Date updateTime;



}
