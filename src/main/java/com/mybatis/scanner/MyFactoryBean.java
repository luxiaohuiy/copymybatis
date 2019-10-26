package com.mybatis.scanner;


import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

public class MyFactoryBean implements FactoryBean {

    Class mapperinterface;

    public MyFactoryBean(Class mapperinterface){
        this.mapperinterface = mapperinterface;
    }

    public Object getObject() throws Exception {
        Object o = Proxy.newProxyInstance(MyFactoryBean.class.getClassLoader(), new Class[]{mapperinterface}, new MyHandler());
        return o;
    }

    public Class<?> getObjectType() {
        return mapperinterface;
    }
}
