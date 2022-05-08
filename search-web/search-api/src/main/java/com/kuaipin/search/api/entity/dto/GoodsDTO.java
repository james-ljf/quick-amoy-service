package com.kuaipin.search.api.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: ljf
 * @DateTime: 2022/5/7 10:29
 */
@Data
public class GoodsDTO {

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 编号（对外暴露）
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
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

}
