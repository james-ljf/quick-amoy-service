package com.kuaipin.search.server.repository.impl;

import com.github.pagehelper.PageInfo;
import com.kuaipin.common.entity.Page;
import com.kuaipin.search.api.entity.SearchRecordDTO;
import com.kuaipin.search.server.repository.SearchRepository;
import com.kuaipin.search.server.repository.impl.mapper.SearchMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: ljf
 * @DateTime: 2021/12/13 14:39
 */
@Component
public class SearchRepositoryImpl implements SearchRepository {

    @Resource
    private SearchMapper dbSearchRecordProxy;

    @Override
    public Page<SearchRecordDTO> findAllSearchRecord() {
        List<SearchRecordDTO> searchRecordDTOList = dbSearchRecordProxy.selectSearchRecordByPage();
        if (CollectionUtils.isNotEmpty(searchRecordDTOList)){
            // 将结果分页
            PageInfo<SearchRecordDTO> pageInfo = new PageInfo<>(searchRecordDTOList);
            return new Page<>(pageInfo.getTotal(), searchRecordDTOList);
        }
        return null;
    }
}
