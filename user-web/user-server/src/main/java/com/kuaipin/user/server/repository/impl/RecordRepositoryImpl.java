package com.kuaipin.user.server.repository.impl;

import com.github.pagehelper.PageInfo;
import com.kuaipin.common.entity.Page;
import com.kuaipin.user.server.entity.po.BrowseRecord;
import com.kuaipin.user.server.entity.po.SearchRecord;
import com.kuaipin.user.server.repository.RecordRepository;
import com.kuaipin.user.server.repository.impl.mapper.RecordMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: ljf
 * @DateTime: 2021/12/13 14:39
 */
@Repository
public class RecordRepositoryImpl implements RecordRepository {

    @Resource
    private RecordMapper dbSearchRecordProxy;

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

    @Override
    public List<SearchRecord> findSearchRecordByTime(Long uid, Integer size) {
        return dbSearchRecordProxy.selectSearchRecordByTime(uid, size);
    }

    @Override
    public List<BrowseRecord> findBrowseRecordByUid(Long uid, Integer size) {
        return dbSearchRecordProxy.selectBrowseRecordByUid(uid, size);
    }
}
