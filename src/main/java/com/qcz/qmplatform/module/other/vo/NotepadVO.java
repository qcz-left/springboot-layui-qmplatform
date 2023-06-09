package com.qcz.qmplatform.module.other.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class NotepadVO implements Serializable {

    /**
     * 标题
     */
    private String title;

    /**
     * 只查询自己
     */
    private boolean onlySelf;

}
