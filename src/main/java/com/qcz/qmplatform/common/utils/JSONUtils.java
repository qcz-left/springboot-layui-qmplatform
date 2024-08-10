package com.qcz.qmplatform.common.utils;

import cn.hutool.json.JSONUtil;

/**
 * JSON 工具类
 */
public class JSONUtils extends JSONUtil {

    /**
     * 将对象转为格式化后的JSON字符串
     *
     * @param jsonObject 对象
     * @return 格式化后的JSON字符串
     */
    public static String formatJson(Object jsonObject) {
        return formatJsonStr(toJsonStr(jsonObject));
    }

}
