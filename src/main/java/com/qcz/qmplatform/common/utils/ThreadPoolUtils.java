package com.qcz.qmplatform.common.utils;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.ExecutorService;

/**
 * 全局线程池工具
 */
public class ThreadPoolUtils {

    private static final ExecutorService EXECUTOR_SERVICE = ThreadUtil.newExecutor(50);

    public static void execute(Runnable runnable) {
        EXECUTOR_SERVICE.submit(runnable);
    }
}
