package com.qcz.qmplatform.filter;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.constant.ResponseCode;
import com.qcz.qmplatform.common.utils.HttpServletUtils;
import com.qcz.qmplatform.common.utils.JSONUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class ShiroLoginTimeoutFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                return true;
            }
        } else {
            if (HttpServletUtils.isAjax(request)) {
                ServletUtil.write(httpServletResponse, JSONUtils.toJsonStr(ResponseResult.newInstance(ResponseCode.AUTHORIZED_EXPIRE)), ContentType.JSON.getValue());
                return false;
            } else {
                saveRequestAndRedirectToLogin(request, response);
            }
        }
        return false;
    }
}
