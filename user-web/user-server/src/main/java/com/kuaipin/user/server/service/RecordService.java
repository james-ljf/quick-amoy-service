package com.kuaipin.user.server.service;

import com.kuaipin.common.entity.Page;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.user.server.entity.po.BrowseRecord;
import com.kuaipin.user.server.entity.po.SearchRecord;

import java.util.List;

/**
 * 对搜索记录的操作接口
 * @Author: ljf
 * @DateTime: 2021/12/13 14:24
 */
public interface RecordService {

    /**
     * 查询所有搜索记录
     * @param pageDTO   分页请求体
     * @return  搜索记录分页对象
     */
    Page<SearchRecord> allSearchRecord(PageDTO pageDTO);

    /**
     * 查询用户的搜索历史
     * 根据搜索记录创建时间搜索
     * @param uId  用户id
     * @param size 查询数量
     * @return  用户搜索历史列表
     */
    List<SearchRecord> latelySearchRecord(Long uId, Integer size);

    /**
     * 查询用户的浏览记录
     * @param uId   用户id
     * @param size  查询的数量
     * @return  用户浏览记录列表
     */
    List<BrowseRecord> latelyBrowseRecord(Long uId, Integer size);

}
