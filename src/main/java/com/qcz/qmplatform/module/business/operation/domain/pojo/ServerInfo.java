package com.qcz.qmplatform.module.business.operation.domain.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ServerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Computer computer;

    private Cpu cpu;

    private Mem mem;

    private Disk disk;

}
