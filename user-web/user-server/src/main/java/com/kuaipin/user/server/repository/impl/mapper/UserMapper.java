package com.kuaipin.user.server.repository.impl.mapper;

import com.kuaipin.user.server.entity.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: ljf
 * @DateTime: 2022/4/13 14:27
 */
@Mapper
public interface UserMapper {

    /**
     * 查询用户信息
     * @param email 邮箱号
     * @return  用户信息
     */
    User selectUserInfo(String email);

    /**
     * 查询用户信息
     * @param uId  用户id
     * @return  用户信息
     */
    User selectUserInfoByUid(Long uId);

    /**
     * 插入用户信息
     * @param user  用户信息
     * @return  插入数量
     */
    int insertUser(@Param("user") User user);

    /**
     * 修改用户信息
     * @param user  用户信息
     * @return  修改数量
     */
    int updateUser(User user);


}
