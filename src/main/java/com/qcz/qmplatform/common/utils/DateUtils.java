package com.qcz.qmplatform.common.utils;

import cn.hutool.core.date.DateUtil;

import java.sql.Timestamp;
import java.util.Date;

public class DateUtils extends DateUtil {

    public static Timestamp getCurrTimestamp() {
        return new Timestamp(new Date().getTime());
    }
}
