package com.select.SelectCourse.mapper;

import com.select.SelectCourse.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    public List<User> getAllUser();

    public int InsertUser(User user);

    public int getPower(String uname);

    public User checkUser(String uname, String password);

    public User getUserByName(String uname);

    public List<User> getUserByPower(int power);

    public void modifyUname(User user);

    public void deleteUserByName(String uname);

    public void modifyPassword(User user);

}
