package com.qcz.qmplatform.module.system.vo;

import com.qcz.qmplatform.module.system.domain.OperateLog;

public class OperateLogTimeVO extends OperateLog {

    private String operateTime_start;
    private String operateTime_end;

    public String getOperateTime_start() {
        return operateTime_start;
    }

    public void setOperateTime_start(String operateTime_start) {
        this.operateTime_start = operateTime_start;
    }

    public String getOperateTime_end() {
        return operateTime_end;
    }

    public void setOperateTime_end(String operateTime_end) {
        this.operateTime_end = operateTime_end;
    }
}
