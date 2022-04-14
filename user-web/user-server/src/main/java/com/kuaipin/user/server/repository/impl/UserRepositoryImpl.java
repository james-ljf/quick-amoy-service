package com.kuaipin.user.server.repository.impl;

import com.kuaipin.user.server.entity.po.User;
import com.kuaipin.user.server.repository.UserRepository;
import com.kuaipin.user.server.repository.impl.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Author: ljf
 * @DateTime: 2022/4/13 14:27
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private UserMapper dbUserProxy;

    @Override
    public User findUserInfo(String email) {
        return dbUserProxy.selectUserInfo(email);
    }

    @Override
    public int setUserInfo(User user) {
        return dbUserProxy.insertUser(user);
    }
}
