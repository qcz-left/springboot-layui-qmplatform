package com.qcz.qmplatform.module.system.vo;

import com.qcz.qmplatform.module.system.domain.OperateLog;

import java.sql.Timestamp;

public class OperateLogTimeVO extends OperateLog {

    /**
     * 操作时间-开始
     */
    private Timestamp operateTimeStart;
    /**
     * 操作时间-截止
     */
    private Timestamp operateTimeEnd;

    public Timestamp getOperateTimeStart() {
        return operateTimeStart;
    }

    public void setOperateTimeStart(Timestamp operateTimeStart) {
        this.operateTimeStart = operateTimeStart;
    }

    public Timestamp getOperateTimeEnd() {
        return operateTimeEnd;
    }

    public void setOperateTimeEnd(Timestamp operateTimeEnd) {
        this.operateTimeEnd = operateTimeEnd;
    }
}
