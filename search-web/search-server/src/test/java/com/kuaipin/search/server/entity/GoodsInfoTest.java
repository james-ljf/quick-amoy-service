package com.kuaipin.search.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: lijf
 * @DateTime: 2022/3/22 11:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoTest implements Serializable {

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

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 商家名字
     */
    private String businessName;
    /**
     * 小品类名字
     */
    private String sTypeName;
    /**
     * 大品类名字
     */
    private String typeName;

}
