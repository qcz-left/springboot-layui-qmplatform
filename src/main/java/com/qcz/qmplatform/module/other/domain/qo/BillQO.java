package com.qcz.qmplatform.module.other.domain.qo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class BillQO implements Serializable {

    private String remark;

    private String consumer;

    private String typeId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date consumeTimeStart;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date consumeTimeEnd;

}
