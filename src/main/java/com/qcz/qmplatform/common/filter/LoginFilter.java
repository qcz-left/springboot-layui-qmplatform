package com.qcz.qmplatform.common.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.qcz.qmplatform.common.message.ResponseResult;
import com.qcz.qmplatform.common.utils.HttpServletUtils;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;

/**
 * 登录过滤器，解决shiro无法拦截ajax请求的问题
 * @author changzhongq
 * @time 2018年11月22日 下午10:17:41
 */
public class LoginFilter extends FormAuthenticationFilter {

	@Value("${server.servlet.context-path}")
	private String contextPath;

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		HttpServletResponse resp = (HttpServletResponse) response;
		if (HttpServletUtils.isAjax(request)) {
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json");
			ResponseResult resultData = new ResponseResult();
			resultData.setData(0);
			resultData.setIsSuccess(false);
			resultData.setMsg("登录认证失效,请重新登录!");
			resp.getWriter().write(new Gson().toJson(resultData));
		} else {
			resp.sendRedirect(contextPath + "/main/loginPage");
		}
		return false;
	}

}
