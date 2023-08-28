package com.qcz.qmplatform.module.other.domain.vo;

import com.qcz.qmplatform.module.other.domain.Bill;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class BillVO extends Bill {

    private String typeName;

}
