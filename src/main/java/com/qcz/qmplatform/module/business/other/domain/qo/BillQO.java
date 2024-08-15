package com.qcz.qmplatform.module.business.other.domain.qo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class BillQO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String remark;

    private String consumer;

    private String typeId;

    private LocalDate consumeTimeStart;

    private LocalDate consumeTimeEnd;

}
