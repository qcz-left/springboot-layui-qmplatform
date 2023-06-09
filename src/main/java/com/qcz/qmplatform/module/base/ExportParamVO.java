package com.qcz.qmplatform.module.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 导出参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ExportParamVO extends ExcelTemplateVO {

    /**
     * 查询数据的url
     */
    private String queryUrl;

    /**
     * 查询参数
     */
    private Map<String, Object> queryParam;


}
