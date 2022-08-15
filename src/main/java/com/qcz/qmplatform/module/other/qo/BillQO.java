package com.qcz.qmplatform.module.other.qo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class BillQO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String remark;

    private String typeId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date consumeTimeStart;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date consumeTimeEnd;

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

    public Date getConsumeTimeStart() {
        return consumeTimeStart;
    }

    public void setConsumeTimeStart(Date consumeTimeStart) {
        this.consumeTimeStart = consumeTimeStart;
    }

    public Date getConsumeTimeEnd() {
        return consumeTimeEnd;
    }

    public void setConsumeTimeEnd(Date consumeTimeEnd) {
        this.consumeTimeEnd = consumeTimeEnd;
    }
}
