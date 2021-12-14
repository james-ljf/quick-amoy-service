package com.kuaipin.search.server.repository.impl.mapper;

import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.search.server.entity.po.SearchRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用于操作search_record数据表
 * @Author: ljf
 * @DateTime: 2021/12/13 14:40
 */
@Mapper
public interface SearchRecordMapper {

    /**
     * 分页查询所有搜索记录
     * @return  搜索记录列表
     */
    @Select("select id, search_id, search_keyword, search_extend, u_id, create_time, update_time" +
            " from search_record")
    List<SearchRecord> selectSearchRecordByPage();

}
