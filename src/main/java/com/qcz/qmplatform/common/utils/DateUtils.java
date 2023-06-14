package com.qcz.qmplatform.common.utils;

import cn.hutool.core.date.DateUtil;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtils extends DateUtil {

    /**
     * 获取当前时间对象Timestamp
     */
    public static Timestamp getCurrTimestamp() {
        return timestamp(new Date());
    }

    /**
     * 将Date对象转化为Timestamp对象
     */
    public static Timestamp timestamp(Date date) {
        return timestamp(date.getTime());
    }

    /**
     * 根据时间戳获取Timestamp对象
     *
     * @param time 时间戳
     */
    public static Timestamp timestamp(long time) {
        return new Timestamp(time);
    }

    public static long getLocalDateTimeLong(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDateTime getLocalDateTime(long time) {
        return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
