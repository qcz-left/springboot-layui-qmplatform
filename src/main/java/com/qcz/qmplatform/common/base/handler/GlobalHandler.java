package com.qcz.qmplatform.common.base.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局处理
 * @author changzhongq
 * @time 2018年11月15日 上午10:40:05
 */
@ControllerAdvice
public class GlobalHandler {

	/**
	 * 返回页面路径前缀
	 */
	private static final String PATH_PREFIX = "/error/";

	/**
	 * 服务器异常500
	 */
	@ExceptionHandler(Exception.class)
	public String errorHandleBy500(Exception ex) {
		return PATH_PREFIX + "500";
	}

	/**
	 * 页面找不到404
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	public String errorHandleBy404(NoHandlerFoundException ex) {
		return PATH_PREFIX + "404";
	}
	
}
