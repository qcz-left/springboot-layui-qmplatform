package com.qcz.qmplatform.module.other.qo;

import java.io.Serializable;
import java.sql.Timestamp;

public class BillQO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String remark;

    private String typeId;

    private Timestamp consumeTimeStart;

    private Timestamp consumeTimeEnd;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Timestamp getConsumeTimeStart() {
        return consumeTimeStart;
    }

    public void setConsumeTimeStart(Timestamp consumeTimeStart) {
        this.consumeTimeStart = consumeTimeStart;
    }

    public Timestamp getConsumeTimeEnd() {
        return consumeTimeEnd;
    }

    public void setConsumeTimeEnd(Timestamp consumeTimeEnd) {
        this.consumeTimeEnd = consumeTimeEnd;
    }
}
