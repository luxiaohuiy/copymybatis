package com.mybatis.dao;

import com.mybatis.entity.User;
import com.mybatis.annotion.MySelect;

import java.util.List;

public interface UserDao {

    @MySelect("select * from user")
    List<User> queryAll();
}
