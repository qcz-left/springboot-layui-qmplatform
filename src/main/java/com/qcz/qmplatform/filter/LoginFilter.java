package com.qcz.qmplatform.filter;

import cn.hutool.json.JSONUtil;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.constant.ResponseCode;
import com.qcz.qmplatform.common.utils.HttpServletUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录过滤器，解决shiro无法拦截ajax请求的问题
 *
 * @author changzhongq
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
            ResponseResult<?> resultData = new ResponseResult<>();
            resultData.setCode(ResponseCode.AUTHORIZED_EXPIRE.code());
            resultData.setMsg("登录认证失效，请重新登录!");
            resp.getWriter().write(JSONUtil.toJsonStr(resultData));
        } else {
            resp.sendRedirect(contextPath + "/loginPage?code=402");
        }
        return false;
    }

}
