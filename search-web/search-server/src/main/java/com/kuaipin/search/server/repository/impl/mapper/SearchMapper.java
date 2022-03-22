package com.kuaipin.search.server.repository.impl.mapper;

import com.kuaipin.search.api.entity.SearchRecordDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 用于操作search_record数据表
 * @Author: ljf
 * @DateTime: 2021/12/13 14:40
 */
@Mapper
public interface SearchMapper {

    /**
     * 分页查询所有搜索记录
     * @return  搜索记录列表
     */
    List<SearchRecordDTO> selectSearchRecordByPage();

}
