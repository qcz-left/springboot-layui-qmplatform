package com.qcz.qmplatform.common.utils;

import cn.hutool.core.io.IoUtil;
import com.alibaba.druid.support.json.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * http 工具类
 *
 * @author changzhongq
 */
public class HttpServletUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpServletUtils.class);

    /**
     * 解析Request里面所有的参数值
     *
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
     *
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

    public static String getJsonParam(HttpServletRequest request) {
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            String read = IoUtil.read(reader);
            return read;
        } catch (IOException ex) {
            log.error("there was an error parsing the Request Body parameter", ex);
        } finally {
            CloseUtils.close(reader);
        }
        return "{}";
    }

    /**
     * 获取请求完整路径
     *
     * @param request the request
     * @return the full request url
     */
    public static String getFullRequestUrl(HttpServletRequest request) {
        return getLocalIpAddress() + ":" + request.getServerPort() + request.getContextPath();
    }

    /**
     * 获取ip地址
     *
     * @param request
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

    public static String getLocalIpAddress() {
        return getLocalIp4Address().getHostAddress();
    }

    public static boolean isAjax(ServletRequest request) {
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
        if ("XMLHttpRequest".equalsIgnoreCase(header)) {
            return true;
        }
        return false;
    }

    public static Inet4Address getLocalIp4Address() {
        List<Inet4Address> ipByNi = null;
        try {
            ipByNi = getLocalIp4AddressFromNetworkInterface();
            if (ipByNi.isEmpty() || ipByNi.size() > 1) {
                final Inet4Address ipBySocketOpt = getIpBySocket();
                if (ipBySocketOpt != null) {
                    return ipBySocketOpt;
                } else {
                    return ipByNi.isEmpty() ? null : ipByNi.get(0);
                }
            }
        } catch (SocketException e) {
            log.error("", e);
        }
        return ipByNi.get(0);
    }

    private static Inet4Address getIpBySocket() throws SocketException {
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            if (socket.getLocalAddress() instanceof Inet4Address) {
                return (Inet4Address) socket.getLocalAddress();
            }
        } catch (UnknownHostException e) {
            log.error("", e);
        }
        return null;
    }

    public static List<Inet4Address> getLocalIp4AddressFromNetworkInterface() throws SocketException {
        List<Inet4Address> addresses = new ArrayList<>(1);
        Enumeration e = NetworkInterface.getNetworkInterfaces();
        if (e == null) {
            return addresses;
        }
        while (e.hasMoreElements()) {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            if (!isValidInterface(n)) {
                continue;
            }
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements()) {
                InetAddress i = (InetAddress) ee.nextElement();
                if (isValidAddress(i)) {
                    addresses.add((Inet4Address) i);
                }
            }
        }
        return addresses;
    }

    /**
     * 过滤回环网卡、点对点网卡、非活动网卡、虚拟网卡并要求网卡名字是eth或ens开头
     *
     * @param ni 网卡
     * @return 如果满足要求则true，否则false
     */
    private static boolean isValidInterface(NetworkInterface ni) throws SocketException {
        return !ni.isLoopback() && !ni.isPointToPoint() && ni.isUp() && !ni.isVirtual() && (ni.getName().startsWith("eth") || ni.getName().startsWith("ens"));
    }

    /**
     * 判断是否是IPv4，并且内网地址并过滤回环地址.
     */
    private static boolean isValidAddress(InetAddress address) {
        return address instanceof Inet4Address && address.isSiteLocalAddress() && !address.isLoopbackAddress();
    }
}
