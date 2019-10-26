package com.mybatis.utils;

import java.sql.*;

public class JDBCUtils {

    private static String driver="com.mysql.cj.jdbc.Driver";
    private static String url="jdbc:mysql://localhost:3306/test?characterEncoding=UTF8&useSSL=false&allowMultiQueries=true&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai";
    private static String user="root";
    private static String password="root";

    private JDBCUtils(){}

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }

    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void colseResource(Connection conn, Statement st, ResultSet rs) {
        closeResultSet(rs);
        closeStatement(st);
        closeConnection(conn);
    }

    public static void closeConnection(Connection conn) {
        if(conn !=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        conn = null;
    }

    public static void closeStatement(Statement st) {
        if(st !=null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        st = null;
    }

    public static void closeResultSet(ResultSet rs) {
        if(rs !=null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        rs = null;
    }

}
