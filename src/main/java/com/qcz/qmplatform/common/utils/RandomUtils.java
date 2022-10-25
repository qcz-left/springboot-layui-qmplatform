package com.qcz.qmplatform.common.utils;

import cn.hutool.core.util.RandomUtil;

public class RandomUtils extends RandomUtil {

    public static String randomIPV4() {
        return StringUtils.format("{}.{}.{}.{}", randomInt(0, 255), randomInt(0, 255), randomInt(0, 255), randomInt(0, 255));
    }

    public static String randomIPV6() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                str.append(randomChar("0123456789ABCDEF"));
            }
            str.append(":");
        }
        String result = str.toString();
        return result.substring(0, result.length() - 1);
    }

    public static String randomMAC() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 2; j++) {
                str.append(randomChar("0123456789ABCDEF"));
            }
            str.append(":");
        }
        String result = str.toString();
        return result.substring(0, result.length() - 1);
    }

}
