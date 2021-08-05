package com.qcz.qmplatform.common.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 具有被观察属性
 */
public interface Observed extends Runnable {

    Logger LOGGER = LoggerFactory.getLogger(Observed.class);

    /**
     * 观察者列表
     */
    List<Observable> OBSERVABLE_LIST = new ArrayList<>();

    BlockingQueue<String> MSG_QUEUE = new LinkedBlockingQueue<>();

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
        MSG_QUEUE.add(msg);
    }

    @Override
    default void run() {
        while (true) {
            try {
                String msg = MSG_QUEUE.take();
                for (Observable observable : OBSERVABLE_LIST) {
                    observable.receiveMessage(msg);
                }
            } catch (InterruptedException e) {
                LOGGER.error("", e);
            }
        }
    }

}