package com.qcz.qmplatform.common.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 导入结果
 */
@Data
@Accessors(chain = true)
public class ImportResult implements Serializable {

    /**
     * 标题
     */
    private String title;

    /**
     * 导入总记录数
     */
    private int total;

    /**
     * 导入成功记录数
     */
    private int successCount;

    /**
     * 导入失败记录数
     */
    private int failCount;

    private List<ImportFailReason> importFailReasonList;

}
