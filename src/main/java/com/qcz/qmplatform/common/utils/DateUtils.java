package com.qcz.qmplatform.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtils extends DateUtil {

    /**
     * 获取当前时间对象LocalDateTime
     */
    public static LocalDateTime getCurrLocalDateTime() {
        return localDateTime(new Date());
    }

    /**
     * 将Date对象转化为LocalDateTime对象
     */
    public static LocalDateTime localDateTime(Date date) {
        return localDateTime(date.getTime());
    }

    /**
     * 根据时间戳获取LocalDateTime对象
     *
     * @param time 时间戳
     */
    public static LocalDateTime localDateTime(long time) {
        return LocalDateTimeUtil.of(time);
    }

    public static long getLocalDateTimeLong(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDateTime getLocalDateTime(long time) {
        return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
