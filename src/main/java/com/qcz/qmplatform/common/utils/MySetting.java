package com.qcz.qmplatform.common.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.watch.SimpleWatcher;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.WatchUtil;
import cn.hutool.core.io.watch.watchers.DelayWatcher;
import cn.hutool.core.lang.Assert;
import cn.hutool.log.StaticLog;
import cn.hutool.setting.Setting;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.function.Consumer;

/**
 * 自定义 Setting
 *
 * @see Setting
 */
public class MySetting extends Setting {

    private WatchMonitor watchMonitor;

    public MySetting(String path) {
        super(path);
    }

    /**
     * 在配置文件变更时自动加载（解决监听回调多次的问题）
     *
     * @param callback   加载完成回调
     * @param autoReload 是否自动加载
     */
    @Override
    public void autoLoad(boolean autoReload, Consumer<Boolean> callback) {
        if (autoReload) {
            Assert.notNull(this.resource, "Setting resource must be not null !");
            if (null != this.watchMonitor) {
                // 先关闭之前的监听
                this.watchMonitor.close();
            }
            this.watchMonitor = WatchUtil.createModify(resource.getUrl(), new DelayWatcher(new SimpleWatcher() {
                @Override
                public void onModify(WatchEvent<?> event, Path currentPath) {
                    boolean success = load();
                    // 如果有回调，加载完毕则执行回调
                    if (callback != null) {
                        callback.accept(success);
                    }
                }
            }, 500));
            this.watchMonitor.start();
            StaticLog.debug("Auto load for [{}] listening...", this.resource.getUrl());
        } else {
            IoUtil.close(this.watchMonitor);
            this.watchMonitor = null;
        }
    }
}
