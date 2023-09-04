package com.qcz.qmplatform.module.business.other.domain.pojo;

import com.qcz.qmplatform.common.bean.Tree;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class BillTypeTree extends Tree {

    private String remark;

}
