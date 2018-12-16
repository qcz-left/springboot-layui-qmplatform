package com.qcz.qmplatform.common.utils;

import org.springframework.context.ApplicationContext;

/**
 * spring上下文工具类
 * @author changzhongq
 * @time 2018年6月16日 上午12:22:20
 */
public class SpringContextUtil {

	private static ApplicationContext applicationContext;

	// 获取上下文
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	// 设置上下文
	public static void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextUtil.applicationContext = applicationContext;
	}

	// 通过名字获取上下文中的bean
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	// 通过类型获取上下文中的bean
	public static Object getBean(Class<?> requiredType) {
		return applicationContext.getBean(requiredType);
	}

}