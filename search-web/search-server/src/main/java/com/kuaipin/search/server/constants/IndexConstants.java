package com.kuaipin.search.server.constants;

/**
 * 索引字段常量
 * @Author: ljf
 * @DateTime: 2022/3/22 16:14
 */
public interface IndexConstants {

    /**
     * 商品id
     */
    String GOODS_ID = "goods_id";
    /**
     * 编号（对外暴露）
     */
    String GOODS_NUMBER = "goods_number";
    /**
     * 名称
     */
    String GOODS_NAME = "goods_name";
    /**
     * 品牌
     */
    String GOODS_BRAND = "goods_brand";
    /**
     * 图片地址
     */
    String GOODS_PIC = "goods_pic";
    /**
     * 规格/版本
     */
    String GOODS_EDITION = "goods_edition";
    /**
     * 价格
     */
    String GOODS_PRICE = "goods_price";
    /**
     * 评论数量
     */
    String GOODS_COMMENT = "goods_comment";
    /**
     * 小品类id
     */
    String S_TYPE_ID = "s_type_id";
    /**
     * 小品类名字
     */
    String S_TYPE_NAME = "s_type_name";
    /**
     * 商家id
     */
    String BUSINESS_ID = "business_id";
    /**
     * 商家名称
     */
    String BUSINESS_NAME = "business_name";
    /**
     * 商家是否是旗舰店 1 是 0 不是
     */
    String IS_FLAGSHIP = "is_flagship";
    /**
     * 大品类名字
     */
    String TYPE_NAME = "type_name";
    /**
     * 创建时间
     */
    String CREATE_TIME = "create_time";

}
