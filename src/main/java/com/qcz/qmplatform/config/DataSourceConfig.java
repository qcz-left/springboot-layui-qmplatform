package com.qcz.qmplatform.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.jakarta.StatViewServlet;
import com.alibaba.druid.support.jakarta.WebStatFilter;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean
    public DatabaseIdProvider getDatabaseIdProvider() {
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties p = new Properties();
        p.setProperty("PostgreSQL", "pg");
        p.setProperty("MySQL", "mysql");
        databaseIdProvider.setProperties(p);
        return databaseIdProvider;
    }

    /**
     * 配置Druid后台监控
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet() {
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

        //增加配置
        HashMap<String, String> initParametersMap = new HashMap<>();

        //后台需要有人登录，账号密码配置
        initParametersMap.put("loginUsername", "admin");
        initParametersMap.put("loginPassword", "123456");

        //允许谁可以访问
        initParametersMap.put("allow", "");

        //禁止谁能访问
        //initParametersMap.put("deny","127.0.0.1");

        //设置初始化参数
        bean.setInitParameters(initParametersMap);

        return bean;
    }

    /**
     * filter  设置后台监控过滤
     */
    @Bean
    public FilterRegistrationBean<WebStatFilter> webStatFilter() {
        FilterRegistrationBean<WebStatFilter> bean = new FilterRegistrationBean<>();
        WebStatFilter webStatFilter = new WebStatFilter();
        bean.setFilter(webStatFilter);

        //过滤请求map
        Map<String, String> initParametersMap = new HashMap<>();

        //忽略的统计
        initParametersMap.put("exclusions", ".js,.css,/druid/*");

        bean.setInitParameters(initParametersMap);

        return bean;
    }
}
