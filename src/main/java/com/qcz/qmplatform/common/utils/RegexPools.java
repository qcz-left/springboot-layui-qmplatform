package com.qcz.qmplatform.common.utils;

import cn.hutool.core.lang.RegexPool;

/**
 * 正则表达式常量
 */
public interface RegexPools extends RegexPool {

    /**
     * 手机号
     */
    String PHONE = "^1\\d{10}$";
}
