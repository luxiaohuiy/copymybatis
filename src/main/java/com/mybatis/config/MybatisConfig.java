package com.mybatis.config;

import com.mybatis.annotion.MyMapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MyMapperScan("com.mybatis.dao")
public class MybatisConfig {

}
