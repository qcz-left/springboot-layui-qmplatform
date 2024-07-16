package com.qcz.qmplatform.common.utils;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 全局线程池工具
 */
@Slf4j
public class ThreadPoolUtils {

    private static final ThreadPoolExecutor EXECUTOR_SERVICE = ThreadUtil.newExecutor(10, 50);

    /**
     * 执行一条线程任务，无返回
     */
    public static void execute(Runnable runnable) {
        log.info("add a task, ActiveCount: {}, MaximumPoolSize: {}", EXECUTOR_SERVICE.getActiveCount(), EXECUTOR_SERVICE.getMaximumPoolSize());
        EXECUTOR_SERVICE.execute(runnable);
    }

    /**
     * 执行一条线程任务，返回 Future 对象
     */
    public static Future<?> submit(Runnable runnable) {
        RunnableFuture<Void> future = new FutureTask<>(runnable, null);
        execute(future);
        return future;
    }
}
