package com.qcz.qmplatform;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qcz.qmplatform.common.filter.LoginFilter;
import com.qcz.qmplatform.common.filter.XSSFilter;

/**
 * 过滤器配置
 * @author changzhongq
 * @time 2018年11月18日 上午12:05:30
 */
@Configuration
public class FilterConfigurer {

	@Bean
	public FilterRegistrationBean<XSSFilter> xssFilter() {
		FilterRegistrationBean<XSSFilter> registration = new FilterRegistrationBean<XSSFilter>();
		registration.setFilter(new XSSFilter());
		registration.addUrlPatterns("/*");
		registration.addInitParameter("paramName", "paramValue");
		registration.setName("xssFilter");
		registration.setOrder(1);
		return registration;
	}

//	@Bean
	public FilterRegistrationBean<LoginFilter> loginFilter() {
		FilterRegistrationBean<LoginFilter> registration = new FilterRegistrationBean<LoginFilter>();
		registration.setFilter(new LoginFilter());
		registration.addUrlPatterns("/*");
		registration.addInitParameter("paramName", "paramValue");
		registration.setName("loginFilter");
		registration.setOrder(1);
		return registration;
	}
}
