package com.qcz.qmplatform.common.bean;

/**
 * 具有被观察属性
 */
public interface Observed {

    /**
     * 添加观察者
     */
    void addObserver(Observable observable);

    /**
     * 移除观察者
     */
    void removeObserver(Observable observable);

    /**
     * 通知消息
     */
    void doNotify(Object msg);
}
