package com.kuaipin.user.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 浏览记录DTO
 * @Author: ljf
 * @DateTime: 2022/4/2 20:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrowseRecordDTO implements Serializable {

    /**
     * 浏览记录id
     */
    private Long browseId;

    /**
     * 商品id
     */
    private Long goodsNumber;

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * 商品小品类id
     */
    private Long sTypeId;

    /**
     * 用户id
     */
    private Long uId;

    /**
     * 创建时间
     */
    private Date createTime;


    @Override
    public String toString() {
        return "BrowseRecord{" +
                "browseId=" + browseId +
                ", goodsNumber='" + goodsNumber + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", sTypeId=" + sTypeId +
                ", uId=" + uId +
                ", createTime=" + createTime +
                '}';
    }

}
