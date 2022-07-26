package com.kuaipin.user.api.service;

import com.kuaipin.common.annotation.CallableAPI;
import com.kuaipin.common.entity.Page;
import com.kuaipin.common.entity.dto.PageDTO;
import com.kuaipin.user.api.entity.BrowseRecordDTO;
import com.kuaipin.user.api.entity.SearchRecordDTO;
import com.kuaipin.user.api.entity.UserDTO;

import java.util.List;

/**
 * @Author: ljf
 * @DateTime: 2021/12/13 14:50
 */
public interface RecordProcessService {

    /**
     * 查询所有搜索记录
     * @param pageDTO   分页请求体
     * @return  搜索记录列表
     */
    @CallableAPI(desc = "查询所有搜索记录")
    Page<SearchRecordDTO> allSearchRecord(PageDTO pageDTO);

    /**
     * 查询所有浏览记录
     * @param pageDTO  分页体
     * @return  浏览记录列表
     */
    @CallableAPI(desc = "查询所有浏览记录")
    Page<BrowseRecordDTO> allBrowseRecord(PageDTO pageDTO);

    /**
     * 用于查询用户的搜索历史
     * 根据搜索记录创建时间搜索最近的size条
     * @param uId  用户id
     * @param size 查询数量
     * @return  搜索历史列表
     */
    @CallableAPI(desc = "根据搜索记录创建时间搜索最近的size条")
    List<SearchRecordDTO> latelySearchHistory(Long uId, Integer size);

    /**
     * 获取用户近期浏览记录size条
     * @param uId   用户id
     * @param size  数量
     * @return  用户浏览记录列表
     */
    @CallableAPI(desc = "根据用户id查询近期浏览记录")
    List<BrowseRecordDTO> latelyBrowseRecord(Long uId, Integer size);

    /**
     * 增加搜索记录
     * @param searchRecordDTO   搜索记录
     * @return  增加数量
     */
    @CallableAPI(desc = "增加搜索记录")
    int increaseSearchRecord(SearchRecordDTO searchRecordDTO);

    /**
     * 获取用户信息
     * @return  用户列表
     */
    @CallableAPI(desc = "获取系统用户列表")
    List<UserDTO> getUserList();

}
