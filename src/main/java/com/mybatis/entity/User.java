package com.mybatis.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    private Integer id;
    private String name;
    private Integer age;

    public User(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
