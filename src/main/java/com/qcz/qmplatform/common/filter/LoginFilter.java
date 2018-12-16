package com.qcz.qmplatform.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.qcz.qmplatform.common.utils.SubjectUtils;
import com.qcz.qmplatform.module.sys.entity.User;

/**
 * 废弃
 * @author changzhongq
 * @time 2018年11月22日 下午10:17:41
 */
public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		User user = SubjectUtils.getUser();
		if (user == null) {
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.sendRedirect("/main/loginPage");
			return ;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
