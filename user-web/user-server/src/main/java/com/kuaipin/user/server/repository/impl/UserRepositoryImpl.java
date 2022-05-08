package com.kuaipin.user.server.repository.impl;

import com.kuaipin.user.server.entity.po.User;
import com.kuaipin.user.server.repository.UserRepository;
import com.kuaipin.user.server.repository.impl.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ljf
 * @DateTime: 2022/4/13 14:27
 */
@Repository
public class UserRepositoryImpl implements UserRepository {


    private UserMapper dbUserProxy;
    @Autowired
    private void setDbUserProxy(UserMapper dbUserProxy){
        this.dbUserProxy = dbUserProxy;
    }

    @Override
    public User findUserInfo(String email) {
        return dbUserProxy.selectUserInfo(email);
    }

    @Override
    public User findUserInfo(Long uId) {
        return dbUserProxy.selectUserInfoByUid(uId);
    }

    @Override
    public int setUserInfo(User user) {
        return dbUserProxy.insertUser(user);
    }

    @Override
    public int changeUserInfo(User user) {
        return dbUserProxy.updateUser(user);
    }

    @Override
    public List<User> findUserInfo() {
        return dbUserProxy.selectUserList();
    }
}
