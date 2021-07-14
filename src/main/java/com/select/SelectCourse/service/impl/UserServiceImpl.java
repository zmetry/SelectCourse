package com.select.SelectCourse.service.impl;

import com.select.SelectCourse.entity.User;
import com.select.SelectCourse.mapper.UserMapper;
import com.select.SelectCourse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    @Override
    public int InsertUser(User user) {
        return userMapper.InsertUser(user);
    }

    @Override
    public int getPower(String uname) {
        return userMapper.getPower(uname);
    }

    @Override
    public User checkUser(String uname, String password) {
        return userMapper.checkUser(uname, password);
    }

    @Override
    public User getUserByName(String uname) {
        return userMapper.getUserByName(uname);
    }

    @Override
    public List<User> getUserByPower(int power) {
        return userMapper.getUserByPower(power);
    }

    @Override
    public void modifyUname(User user) {
        userMapper.modifyUname(user);
    }

    @Override
    public void deleteUserByName(String uname) {
        userMapper.deleteUserByName(uname);
    }

    @Override
    public void modifyPassword(User user) {
        userMapper.modifyPassword(user);
    }

}
