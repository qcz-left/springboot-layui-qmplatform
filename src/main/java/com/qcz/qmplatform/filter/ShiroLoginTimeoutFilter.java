package com.qcz.qmplatform.filter;

import cn.hutool.http.ContentType;
import com.qcz.qmplatform.common.bean.ResponseResult;
import com.qcz.qmplatform.common.constant.ResponseCode;
import com.qcz.qmplatform.common.utils.ServletUtils;
import com.qcz.qmplatform.common.utils.JSONUtils;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

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
            if (ServletUtils.isAjax(request)) {
                ServletUtils.write(httpServletResponse, JSONUtils.toJsonStr(ResponseResult.newInstance(ResponseCode.AUTHORIZED_EXPIRE)), ContentType.JSON.getValue());
                return false;
            } else {
                saveRequestAndRedirectToLogin(request, response);
            }
        }
        return false;
    }
}
