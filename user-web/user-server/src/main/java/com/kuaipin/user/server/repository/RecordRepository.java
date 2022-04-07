package com.kuaipin.user.server.repository;

import com.kuaipin.common.entity.Page;
import com.kuaipin.user.server.entity.po.BrowseRecord;
import com.kuaipin.user.server.entity.po.SearchRecord;

import java.util.List;

/**
 * @Author: ljf
 * @DateTime: 2021/12/13 14:39
 */
public interface RecordRepository {

    /**
     * 查询所有搜索记录
     * @return  搜索记录分页列表
     */
    Page<SearchRecord> findAllSearchRecord();

    /**
     * 查询最近的搜索记录
     * @param uid 用户id
     * @param size 数量
     * @return  搜索记录列表
     */
    List<SearchRecord> findSearchRecordByTime(Long uid, Integer size);

    /**
     * 查询用户最近的浏览记录
     * @param uid   用户id
     * @param size  数量
     * @return  浏览记录列表
     */
    List<BrowseRecord> findBrowseRecordByUid(Long uid, Integer size);

}
