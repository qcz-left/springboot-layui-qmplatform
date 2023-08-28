package com.qcz.qmplatform.module.other.domain.qo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 账单类型查询条件
 * </p>
 */
@Data
@Accessors(chain = true)
public class BillTypeQO implements Serializable {

    /**
     * 账单类型id
     */
    private String id;

    /**
     * select中排除的账单类型ID
     */
    private List<String> notExistsIds;

}
