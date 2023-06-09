package com.qcz.qmplatform.module.operation.vo;

import com.qcz.qmplatform.module.operation.pojo.DBDetail;
import com.qcz.qmplatform.module.operation.pojo.DataDetail;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class MakeDataVO implements Serializable {

    private DBDetail dbDetail;

    private DataDetail dataDetail;

    private long insertNumber;

}
