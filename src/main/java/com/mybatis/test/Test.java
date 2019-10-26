package com.mybatis.test;

import com.mybatis.config.MybatisConfig;
import com.mybatis.dao.UserDao;
import com.mybatis.entity.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MybatisConfig.class);

        UserDao dao = context.getBean(UserDao.class);
        List<User> userList = dao.queryAll();

        System.out.println(userList);


    }
}
