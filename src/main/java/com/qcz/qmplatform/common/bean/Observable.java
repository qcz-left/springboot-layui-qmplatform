package com.qcz.qmplatform.common.bean;

/**
 * 具有观察行为
 */
public interface Observable {

    void receiveMessage(Object msg);
}
