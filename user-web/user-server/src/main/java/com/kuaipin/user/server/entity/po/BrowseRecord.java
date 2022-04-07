package com.kuaipin.user.server.entity.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 浏览记录
 * @Author: ljf
 * @DateTime: 2022/4/2 14:00
 */
public class BrowseRecord implements Serializable {

    /**
     * 浏览记录id
     */
    private Long browseId;

    /**
     * 商品id
     */
    private String goodsId;

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

    /**
     * 修改时间
     */
    private Date updateTime;

    @Override
    public String toString() {
        return "BrowseRecord{" +
                "browseId=" + browseId +
                ", goodsId='" + goodsId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", sTypeId=" + sTypeId +
                ", uId=" + uId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
