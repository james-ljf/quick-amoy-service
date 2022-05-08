package com.kuaipin.user.server.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

/**
 * 浏览记录
 * @Author: ljf
 * @DateTime: 2022/4/2 14:00
 */
@Data
public class BrowseRecord implements Serializable {

    /**
     * 浏览记录id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long browseId;

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
        return new StringJoiner(", ", BrowseRecord.class.getSimpleName() + "[", "]")
                .add("browseId=" + browseId)
                .add("goodsNumber=" + goodsNumber)
                .add("goodsName='" + goodsName + "'")
                .add("goodsPic='" + goodsPic + "'")
                .add("sTypeId=" + sTypeId)
                .add("uId=" + uId)
                .add("createTime=" + createTime)
                .add("updateTime=" + updateTime)
                .toString();
    }
}
