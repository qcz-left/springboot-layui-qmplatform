package com.qcz.qmplatform.intercept;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;

/**
 * 和PageHelper一起使用，在PageHelper拦截器后添加
 */
@Configuration
public class SqlSessionListener {

    @Autowired
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

