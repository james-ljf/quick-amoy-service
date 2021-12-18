package com.kuaipin.search.server.repository.impl;

import com.github.pagehelper.PageInfo;
import com.kuaipin.common.entity.Page;
import com.kuaipin.search.server.entity.po.SearchRecord;
import com.kuaipin.search.server.repository.SearchRepository;
import com.kuaipin.search.server.repository.impl.mapper.SearchRecordMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: ljf
 * @DateTime: 2021/12/13 14:39
 */
@Component
public class SearchRepositoryImpl implements SearchRepository {

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
