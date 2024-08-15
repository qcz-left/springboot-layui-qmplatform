package com.qcz.qmplatform.module.business.system.domain.pojo;

import com.qcz.qmplatform.common.bean.Tree;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class UserGroupTree extends Tree implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 备注
     */
    private String remark;
}
