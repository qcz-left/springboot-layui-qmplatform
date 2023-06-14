package com.qcz.qmplatform.common.utils;

import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;

/**
 * 定时任务工具类
 */
public class CronUtils extends CronUtil {

    public static String resetSchedule(String id, String pattern, Task task) {
        remove(id);
        return schedule(id, pattern, task);
    }

}
