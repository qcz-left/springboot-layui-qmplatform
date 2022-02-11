package com.qcz.qmplatform.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * http 工具类
 *
 * @author changzhongq
 */
public class HttpServletUtils {

    /**
     * 获取 请求协议+ip+port
     *
     * @param request the request
     */
    public static String getServerPath(HttpServletRequest request) {
        return request.getScheme() + "://127.0.0.1:" + request.getServerPort();
    }

    /**
     * 获取请求ip地址
     *
     * @param request the request
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (StringUtils.equals(ip, "0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    public static boolean isAjax(ServletRequest request) {
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
        return "XMLHttpRequest".equalsIgnoreCase(header);
    }

    /**
     * 获取当前http request请求
     */
    public static HttpServletRequest getCurrRequest() {
        return getServletRequestAttributes().getRequest();
    }

    /**
     * 获取当前http response请求
     */
    public static HttpServletResponse getCurrResponse() {
        return getServletRequestAttributes().getResponse();
    }

    public static ServletRequestAttributes getServletRequestAttributes() {
        return Objects.requireNonNull((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
    }
}
