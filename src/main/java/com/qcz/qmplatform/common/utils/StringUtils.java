package com.qcz.qmplatform.common.utils;

import cn.hutool.core.util.StrUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends StrUtil {

    private static Pattern ENC_PATTERN = Pattern.compile("(ENC\\().*\\)");

    public static void main(String[] args) {
        System.out.println("ENC(123)".replaceAll("(ENC\\().*\\)", "{}"));
        Matcher matcher = ENC_PATTERN.matcher("ENC(123);ENC(456)");
        matcher.find();
        System.out.println(matcher.group(0));

    }
}
