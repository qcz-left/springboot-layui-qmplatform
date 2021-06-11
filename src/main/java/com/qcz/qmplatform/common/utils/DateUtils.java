package com.qcz.qmplatform.common.utils;

import cn.hutool.core.date.DateUtil;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public static long getLocalDateTimeLong(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDateTime getLocalDateTime(long time) {
        return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static void main(String[] args) {

    }

}
