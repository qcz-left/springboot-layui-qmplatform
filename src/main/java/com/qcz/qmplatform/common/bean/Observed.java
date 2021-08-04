package com.qcz.qmplatform.common.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 具有被观察属性
 */
public interface Observed {

    /**
     * 观察者列表
     */
    List<Observable> OBSERVABLE_LIST = new ArrayList<>();

    /**
     * 添加观察者
     */
    default void addObserver(Observable observable) {
        OBSERVABLE_LIST.add(observable);
    }

    /**
     * 移除观察者
     */
    default void removeObserver(Observable observable) {
        OBSERVABLE_LIST.remove(observable);
    }

    /**
     * 通知消息
     */
    default void doNotify(String msg) {
        for (Observable observable : OBSERVABLE_LIST) {
            observable.receiveMessage(msg);
        }
    }

}