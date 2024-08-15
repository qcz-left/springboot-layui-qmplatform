package com.qcz.qmplatform.module.business.operation.domain.vo;

import com.qcz.qmplatform.module.business.operation.domain.pojo.DBDetail;
import com.qcz.qmplatform.module.business.operation.domain.pojo.DataDetail;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class MakeDataVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private DBDetail dbDetail;

    private DataDetail dataDetail;

    private long insertNumber;

}
