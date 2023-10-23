package com.qcz.qmplatform.common.utils;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.net.Ipv4Util;

public class IpUtils extends Ipv4Util {

    public static boolean isIp(String ip) {
        return PatternPool.IPV4.matcher(ip).matches() || PatternPool.IPV6.matcher(ip).matches();
    }
}
