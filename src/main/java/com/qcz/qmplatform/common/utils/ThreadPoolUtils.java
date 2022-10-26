package com.qcz.qmplatform.common.utils;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 全局线程池工具
 */
public class ThreadPoolUtils {

    private static final ExecutorService EXECUTOR_SERVICE = ThreadUtil.newExecutor(50);

    public static Future<?> execute(Runnable runnable) {
        return EXECUTOR_SERVICE.submit(runnable);
    }
}
