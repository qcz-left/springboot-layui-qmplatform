package com.qcz.qmplatform;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置静态资源文件
 * @author quchangzhong
 * @time 2018年1月20日 下午10:19:48
 */
@EnableWebMvc
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**/*.html").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/templates/");
		registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
	}

}