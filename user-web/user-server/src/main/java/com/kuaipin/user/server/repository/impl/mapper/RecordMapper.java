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
     * 查询所有搜索记录
     * @param uId  用户id
     * @return  搜索记录列表
     */
    List<SearchRecord> selectAllSearchRecord(@Param("uid") Long uId);

    /**
     * 按照创建时间查询搜索记录
     * @param uId   用户id
     * @param size  数量
     * @return  搜索记录列表
     */
    List<SearchRecord> selectSearchRecordByTime(@Param("uid") Long uId, @Param("size") Integer size);

    /**
     * 按照关键词查询搜索记录
     * @param keyword  关键词
     * @param uId  用户id
     * @return  搜索记录
     */
    SearchRecord selectSearchRecord(String keyword, Long uId);

    /**
     * 根据关键词查询搜索记录
     * @param keyword   关键词
     * @return  搜索记录
     */
    SearchRecord selectSearchRecordByKeyword(String keyword);

    /**
     * 查询所有浏览记录
     * @param uId  用户id
     * @return  浏览记录列表
     */
    List<BrowseRecord> selectAllBrowseRecord(@Param("uid") Long uId);

    /**
     * 按照用户id查询近期浏览记录
     * @param uId   用户id
     * @param size  数量
     * @return  浏览记录列表
     */
    List<BrowseRecord> selectBrowseRecordByUid(@Param("uid") Long uId, @Param("size") Integer size);

    /**
     * 根据商品编号查询浏览记录
     * @param goodsNumber   商品编号
     * @param uId  用户id
     * @return  浏览记录
     */
    BrowseRecord selectBrowseRecordByGoodsNumber(@Param("gNumber") Long goodsNumber, @Param("uid") Long uId);

    /**
     * 分页查询所有浏览记录
     * @param uId 用户id
     * @return  浏览记录列表
     */
    List<BrowseRecord> selectBrowseRecordByPage(@Param("uid") Long uId);

    /**
     * 插入搜索记录
     * @param searchRecord  搜索记录
     * @return  插入数量
     */
    int insertSearchRecord(@Param("sr") SearchRecord searchRecord);

    /**
     * 插入浏览记录
     * @param browseRecord  浏览记录
     * @return  插入数量
     */
    int insertBrowseRecord(@Param("br") BrowseRecord browseRecord);

    /**
     * 删除搜索记录
     * @param searchId  搜索记录
     * @param uId 用户id
     * @return  删除数量
     */
    int deleteSearchRecord(@Param("sid") Long searchId, @Param("uid") Long uId);

    /**
     * 删除浏览记录
     * @param browseId  浏览记录id
     * @param uId   用户id
     * @return  删除数量
     */
    int deleteBrowseRecord(@Param("bid") Long browseId, @Param("uid") Long uId);

    /**
     * 修改搜索祭礼
     * @param searchRecord  搜索记录
     * @return  修改数量
     */
    int updateSearchRecord(@Param("sr") SearchRecord searchRecord);

    /**
     * 修改浏览记录
     * @param browseRecord  浏览记录
     * @return  修改数量
     */
    int updateBroseRecord(@Param("br") BrowseRecord browseRecord);

    /**
     * 查询近期浏览记录
     * @param uId  用户id
     * @return  浏览记录列表
     */
    List<BrowseRecord> selectNearBrowseRecord(@Param("uid") Long uId);

    /**
     * 查询更早之前的浏览记录
     * @param uId  用户id
     * @return  浏览记录列表
     */
    List<BrowseRecord> selectLaterBrowseRecord(@Param("uid") Long uId);

}
