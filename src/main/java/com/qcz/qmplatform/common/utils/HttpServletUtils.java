package com.qcz.qmplatform.common.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.druid.support.json.JSONUtils;

/**
 * http 工具类
 * @author changzhongq
 * @time 2018年11月22日 上午9:11:56
 */
public class HttpServletUtils {

	/**
	 * 解析Request里面所有的参数值
	 * @param request HttpServletRequest
	 * @return map对象
	 */
	public static Map<String, Object> parseRequest(HttpServletRequest request) {
		Map<String, Object> params = new HashMap<>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			if (!StringUtils.isBlank(parameterName)) {
				params.put(parameterName, request.getParameter(parameterName));
			}
		}
		return params;
	}

	/**
	 * 只解析request里面的param参数，此方法常用于分页额外请求参数
	 * @param request HttpServletRequest
	 * @return map对象
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseRequestByParam(HttpServletRequest request) {
		Map<String, Object> params = new HashMap<>();
		String param = request.getParameter("param");
		if (param == null) {
			throw new NullPointerException("HttpServletRequest对象中没有发现名为param的参数！");
		}
		if (!StringUtils.isBlank(param)) {
			params = (Map<String, Object>) JSONUtils.parse(param);
		}
		return params;
	}

	/**
	 * 获取ip地址
	 * @param request
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
