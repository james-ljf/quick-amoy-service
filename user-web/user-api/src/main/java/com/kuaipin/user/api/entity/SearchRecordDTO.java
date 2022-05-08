package com.kuaipin.user.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

/**
 * 用户搜索记录DTO
 * @Author: ljf
 * @DateTime: 2021/12/13 14:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRecordDTO implements Serializable {

    /**
     * 搜索记录id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long searchId;
    /**
     * 搜索关键字
     */
    private String searchKeyword;
    /**
     * 搜索的扩展筛选项
     */
    private String searchExtend;

    /**
     * 是否有结果
     */
    private String isResult;
    /**
     * 用户id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long uId;

    private Date createTime;
    @Override
    public String toString() {
        return new StringJoiner(", ", SearchRecordDTO.class.getSimpleName() + "[", "]")
                .add("searchId=" + searchId)
                .add("searchKeyword='" + searchKeyword + "'")
                .add("searchExtend='" + searchExtend + "'")
                .add("isResult='" + isResult + "'")
                .add("uId=" + uId)
                .add("createTime=" + createTime)
                .toString();
    }
}
