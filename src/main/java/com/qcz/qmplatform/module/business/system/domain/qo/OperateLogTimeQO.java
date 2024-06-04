package com.qcz.qmplatform.module.business.system.domain.qo;

import com.qcz.qmplatform.module.business.system.domain.OperateLog;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class OperateLogTimeQO extends OperateLog {

    /**
     * 操作时间-开始
     */
    private LocalDateTime operateTimeStart;
    /**
     * 操作时间-截止
     */
    private LocalDateTime operateTimeEnd;

}
