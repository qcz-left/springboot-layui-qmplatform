package com.qcz.qmplatform.common.utils;

import cn.hutool.cron.CronUtil;

import java.util.Objects;

/**
 * 定时任务工具类
 */
public class CronUtils extends CronUtil {

    /**
     * 是否包含某个定时任务
     *
     * @param id 任务ID
     */
    public static boolean contains(String id) {
        return Objects.nonNull(getScheduler().getPattern(id));
    }

}
