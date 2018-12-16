package com.qcz.qmplatform;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.github.pagehelper.PageHelper;
import com.qcz.qmplatform.common.base.BaseDao;

import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * MyBatis配置
 * @author changzhongq
 * @time 2018年10月20日 下午6:07:23
 */
@Configuration
public class MybatisConfigurer {

	@Autowired
	private DataSource dataSource;

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setTypeAliasesPackage("com.qcz.qmplatform.**.entity");

		// 开启log打印日志和驼峰转换
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.setLogImpl(Slf4jImpl.class);
		bean.setConfiguration(configuration);
		// 添加XML目录
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		bean.setMapperLocations(resolver.getResources("classpath:mapper/**/*Mapper.xml"));
		// 注册pagehelper分页插件
		Interceptor[] plugins = new Interceptor[] { pageHelper() };
		bean.setPlugins(plugins);

		return bean.getObject();
	}

	@Configuration
	@AutoConfigureAfter(MybatisConfigurer.class)
	public static class MyBatisMapperScannerConfigurer {

		@Bean
		public MapperScannerConfigurer mapperScannerConfigurer() {
			MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
			mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
			mapperScannerConfigurer.setBasePackage("com.qcz.qmplatform.module");
			// 配置通用mappers
			Properties properties = new Properties();
			properties.setProperty("mappers", BaseDao.class.getName());
			properties.setProperty("notEmpty", "false");
			properties.setProperty("IDENTITY", "MYSQL");
			mapperScannerConfigurer.setProperties(properties);

			return mapperScannerConfigurer;
		}

	}

	// 配置mybatis的分页插件pageHelper
	@Bean
	public PageHelper pageHelper() {
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("offsetAsPageNum", "true");
		properties.setProperty("rowBoundsWithCount", "true");
		properties.setProperty("reasonable", "true");
		properties.setProperty("dialect", "mysql"); // 配置mysql数据库的方言
		pageHelper.setProperties(properties);
		return pageHelper;
	}

}
