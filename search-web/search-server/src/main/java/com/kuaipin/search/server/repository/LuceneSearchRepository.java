package com.kuaipin.search.server.repository;

import com.kuaipin.common.entity.Page;
import com.kuaipin.search.server.entity.po.SearchRecord;

/**
 * @Author: ljf
 * @DateTime: 2021/12/13 14:39
 */
public interface LuceneSearchRepository {

    /**
     * 查询所有搜索记录
     * @return  搜索记录分页列表
     */
    Page<SearchRecord> findAllSearchRecord();

}
