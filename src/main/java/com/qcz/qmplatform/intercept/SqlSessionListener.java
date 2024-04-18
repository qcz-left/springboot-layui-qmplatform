package com.qcz.qmplatform.intercept;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.Properties;

/**
 * 和PageHelper一起使用，在PageHelper拦截器后添加
 */
public class SqlSessionListener {

    @Resource
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @PostConstruct
    public void addMyInterceptor() {
        MybatisInterceptor interceptor = new MybatisInterceptor();
        interceptor.setProperties(new Properties());
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
        }
    }
}

