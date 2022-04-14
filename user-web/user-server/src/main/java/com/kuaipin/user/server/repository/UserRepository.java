package com.kuaipin.user.server.repository;

import com.kuaipin.user.server.entity.po.User;

/**
 * @Author: ljf
 * @DateTime: 2022/4/13 14:26
 */
public interface UserRepository {

    /**
     * 查询用户信息
     * @param email 邮箱
     * @return  用户信息
     */
    User findUserInfo(String email);

    /**
     * 插入用户信息
     * @param user  用户信息
     * @return  插入数量
     */
    int setUserInfo(User user);

}
