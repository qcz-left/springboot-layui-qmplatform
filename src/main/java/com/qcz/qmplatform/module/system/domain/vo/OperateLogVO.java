package com.qcz.qmplatform.module.system.domain.vo;

import com.qcz.qmplatform.module.system.domain.OperateLog;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class OperateLogVO extends OperateLog {

    private String operateTypeName;

    private String operateStatusName;

}
