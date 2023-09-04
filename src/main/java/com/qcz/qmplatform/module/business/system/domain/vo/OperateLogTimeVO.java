package com.qcz.qmplatform.module.business.system.domain.vo;

import com.qcz.qmplatform.module.business.system.domain.OperateLog;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class OperateLogTimeVO extends OperateLog {

    /**
     * 操作时间-开始
     */
    private Timestamp operateTimeStart;
    /**
     * 操作时间-截止
     */
    private Timestamp operateTimeEnd;

}
