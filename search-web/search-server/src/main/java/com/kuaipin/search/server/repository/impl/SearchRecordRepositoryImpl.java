package com.kuaipin.search.server.repository.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kuaipin.common.entity.Page;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.search.server.entity.po.SearchRecord;
import com.kuaipin.search.server.repository.SearchRecordRepository;
import com.kuaipin.search.server.repository.impl.mapper.SearchRecordMapper;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: ljf
 * @DateTime: 2021/12/13 14:39
 */
public class SearchRecordRepositoryImpl implements SearchRecordRepository {

    @Resource
    private SearchRecordMapper dbSearchRecordProxy;

    @Override
    public Page<SearchRecord> findAllSearchRecord() {
        List<SearchRecord> searchRecordList = dbSearchRecordProxy.selectSearchRecordByPage();
        if (CollectionUtils.isNotEmpty(searchRecordList)){
            // 将结果分页
            PageInfo<SearchRecord> pageInfo = new PageInfo<>(searchRecordList);
            return new Page<>(pageInfo.getTotal(), searchRecordList);
        }
        return null;
    }
}
