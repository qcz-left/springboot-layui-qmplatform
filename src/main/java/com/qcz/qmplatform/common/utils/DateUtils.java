package com.qcz.qmplatform.common.utils;

import cn.hutool.core.date.DateUtil;

import java.sql.Timestamp;
import java.util.Date;

public class DateUtils extends DateUtil {

    public static Timestamp getCurrTimestamp() {
        return timestamp(new Date());
    }

    public static Timestamp timestamp(Date date) {
        return timestamp(date.getTime());
    }

    public static Timestamp timestamp(long time) {
        return new Timestamp(time);
    }

}
