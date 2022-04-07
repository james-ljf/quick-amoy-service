package com.kuaipin.user.server.repository.impl.mapper;

import com.kuaipin.user.server.entity.po.BrowseRecord;
import com.kuaipin.user.server.entity.po.SearchRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用于操作search_record数据表
 * @Author: ljf
 * @DateTime: 2021/12/13 14:40
 */
@Mapper
public interface RecordMapper {

    /**
     * 分页查询所有搜索记录
     * @return  搜索记录列表
     */
    List<SearchRecord> selectSearchRecordByPage();

    /**
     * 按照创建时间查询搜索记录
     * @param uid   用户id
     * @param size  数量
     * @return  搜索记录列表
     */
    List<SearchRecord> selectSearchRecordByTime(@Param("uid") Long uid, @Param("size") Integer size);

    /**
     * 按照用户id查询近期浏览记录
     * @param uid   用户id
     * @param size  数量
     * @return  浏览记录列表
     */
    List<BrowseRecord> selectBrowseRecordByUid(@Param("uid") Long uid, @Param("size") Integer size);

}
