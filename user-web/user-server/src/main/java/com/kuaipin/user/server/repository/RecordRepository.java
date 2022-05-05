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
     * @param uId  用户id
     * @return  搜索记录分页列表
     */
    Page<SearchRecord> getAllSearchRecord(Long uId);

    /**
     * 查询最近的搜索记录
     * @param uid 用户id
     * @param size 数量
     * @return  搜索记录列表
     */
    List<SearchRecord> getSearchRecordByTime(Long uid, Integer size);

    /**
     * 获取用户的搜索记录
     * @param keyword  关键词
     * @param uId  用户id
     * @return  搜索记录
     */
    SearchRecord getSearchRecord(String keyword, Long uId);

    /**
     * 查询用户最近的浏览记录
     * @param uid   用户id
     * @param size  数量
     * @return  浏览记录列表
     */
    List<BrowseRecord> getBrowseRecordByUid(Long uid, Integer size);

    /**
     * 查询所有浏览记录
     * @param uId  用户id
     * @return  浏览记录分页列表
     */
    Page<BrowseRecord> getAllBrowseRecord(Long uId);

    /**
     * 查询用户某条浏览记录
     * @param goodsNumber   商品编号
     * @param uId   用户id
     * @return  浏览记录
     */
    BrowseRecord getBrowseRecord(Long goodsNumber, Long uId);

    /**
     * 添加搜索记录
     * @param searchRecord  搜索记录
     * @return  添加数量
     */
    int setSearchRecord(SearchRecord searchRecord);

    /**
     * 添加浏览记录
     * @param browseRecord  浏览级路
     * @return  添加数量
     */
    int setBrowseRecord(BrowseRecord browseRecord);

    /**
     * 更新搜索记录
     * @param searchRecord  新的搜索记录
     * @return  更新数量
     */
    int modifySearchRecord(SearchRecord searchRecord);
    /**
     * 更新浏览记录
     * @param browseRecord  新的浏览记录
     * @return  更新数量
     */
    int modifyBrowseRecord(BrowseRecord browseRecord);

    /**
     * 删除搜索记录
     * @param searchId  搜索记录id
     * @param uId 用户id
     * @return  删除数量
     */
    int cancelSearchRecord(Long searchId, Long uId);

    /**
     * 删除浏览记录
     * @param browseId  浏览记录id
     * @param uId   用户id
     * @return  删除数量
     */
    int cancelBrowseRecord(Long browseId, Long uId);

    /**
     * 查询近期（7天内）的浏览记录
     * @param uId  用户id
     * @return  浏览记录列表
     */
    List<BrowseRecord> findBrowseRecordInNear(Long uId);

    /**
     * 查询更早之前（7天前）的浏览记录
     * @param uId  用户id
     * @return  浏览记录列表
     */
    List<BrowseRecord> findBrowseRecordInLater(Long uId);

}
