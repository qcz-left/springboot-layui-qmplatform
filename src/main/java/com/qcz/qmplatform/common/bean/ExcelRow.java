package com.qcz.qmplatform.common.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ExcelRow<T> implements Serializable {

    private long rowIndex;

    private T row;

}
