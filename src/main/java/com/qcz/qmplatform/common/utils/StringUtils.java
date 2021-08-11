package com.qcz.qmplatform.common.utils;

import cn.hutool.core.util.StrUtil;

public class StringUtils extends StrUtil {

    /**
     * 字符串长度超过多少时省略显示
     *
     * @param str       需要省略的字符串
     * @param maxLength 最大显示长度
     */
    public static String omit(String str, int maxLength) {
        int strLength = str.length();
        if (strLength <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength) + "...";
    }

}
