package com.kuaipin.search.server.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

/**
 * @Author: ljf
 * @DateTime: 2022/3/22 11:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoVO implements Serializable {

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
     * 小品类id
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
     * 是否是旗舰店（1是,0不是）
     */
    private String isFlagship;
    /**
     * 小品类名字
     */
    private String sTypeName;
    /**
     * 大品类名字
     */
    private String typeName;

    /**
     *  是否是广告位
     */
    private String isAdvertising;

    @Override
    public String toString() {
        return new StringJoiner(", ", GoodsInfoVO.class.getSimpleName() + "[", "]")
                .add("goodsId='" + goodsId + "'")
                .add("goodsNumber=" + goodsNumber)
                .add("goodsName='" + goodsName + "'")
                .add("goodsBrand='" + goodsBrand + "'")
                .add("sTypeId=" + sTypeId)
                .add("goodsPic='" + goodsPic + "'")
                .add("goodsEdition='" + goodsEdition + "'")
                .add("businessId=" + businessId)
                .add("goodsComment=" + goodsComment)
                .add("goodsPrice='" + goodsPrice + "'")
                .add("createTime=" + createTime)
                .add("businessName='" + businessName + "'")
                .add("isFlagship='" + isFlagship + "'")
                .add("sTypeName='" + sTypeName + "'")
                .add("typeName='" + typeName + "'")
                .add("isAdvertising='" + isAdvertising + "'")
                .toString();
    }
}
