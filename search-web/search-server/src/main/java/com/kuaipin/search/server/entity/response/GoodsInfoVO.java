package com.kuaipin.search.server.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

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
        return "GoodsInfoVO{" +
                "goodsId='" + goodsId + '\'' +
                ", goodsNumber=" + goodsNumber +
                ", goodsName='" + goodsName + '\'' +
                ", goodsBrand='" + goodsBrand + '\'' +
                ", sTypeId=" + sTypeId +
                ", goodsPic='" + goodsPic + '\'' +
                ", goodsEdition='" + goodsEdition + '\'' +
                ", businessId=" + businessId +
                ", goodsComment=" + goodsComment +
                ", goodsPrice='" + goodsPrice + '\'' +
                ", createTime=" + createTime +
                ", businessName='" + businessName + '\'' +
                ", isFlagship='" + isFlagship + '\'' +
                ", sTypeName='" + sTypeName + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
