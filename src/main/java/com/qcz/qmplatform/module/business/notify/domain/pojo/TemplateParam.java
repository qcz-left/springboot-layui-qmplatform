package com.qcz.qmplatform.module.business.notify.domain.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class TemplateParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 模板类型
     */
    private int templateType;

    /**
     * 模板ID
     */
    private String templateID;

}
