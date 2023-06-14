package com.qcz.qmplatform.common.bean;

/**
 * 具有观察行为
 */
public interface Observable {

    /**
     * 接收消息
     *
     * @param msg 需要接收的消息
     */
    void receiveMessage(Object msg);
}
