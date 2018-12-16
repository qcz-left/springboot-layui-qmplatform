package com.qcz.qmplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

import com.qcz.qmplatform.common.utils.SpringContextUtil;

@SpringBootApplication
public class QmplatformApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(QmplatformApplication.class, args);
		SpringContextUtil.setApplicationContext(app);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(QmplatformApplication.class);
	}

}