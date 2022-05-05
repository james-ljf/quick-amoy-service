package com.kuaipin.user.server.entity.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kuaipin.user.server.entity.po.BrowseRecord;
import lombok.Data;

import java.util.StringJoiner;

/**
 * @Author: ljf
 * @DateTime: 2022/5/3 13:19
 */
@Data
public class BrowseRecordRequest {

    /**
     * 商品编号
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long goodsNumber;

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * 商品图片
     */
    private String goodsPic;

    /**
     * 商品小品类id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long sTypeId;

    /**
     * 用户id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long uId;


    @Override
    public String toString() {
        return new StringJoiner(", ", BrowseRecord.class.getSimpleName() + "[", "]")
                .add("goodsNumber=" + goodsNumber)
                .add("goodsName='" + goodsName + "'")
                .add("goodsPic='" + goodsPic + "'")
                .add("sTypeId=" + sTypeId)
                .add("uId=" + uId)
                .toString();
    }

}
