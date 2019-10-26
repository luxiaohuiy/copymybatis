package com.mybatis.scanner;

import com.mybatis.utils.JDBCUtils;
import com.mybatis.annotion.MySelect;
import com.mybatis.entity.User;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyHandler implements InvocationHandler {

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<User>();
        try {
            conn = JDBCUtils.getConnection();
            String sql = method.getAnnotation(MySelect.class).value()[0];
            st= conn.prepareStatement(sql);
            ResultSet resultSet = st.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int age = resultSet.getInt("age");
                String name = resultSet.getString("name");

                User user = new User(id,name,age);
                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.colseResource(conn, st, rs);
        }
        return userList;
    }
}
