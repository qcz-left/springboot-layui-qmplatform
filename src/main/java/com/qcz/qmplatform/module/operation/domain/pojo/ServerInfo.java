package com.qcz.qmplatform.module.operation.domain.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ServerInfo implements Serializable {

    private Computer computer;

    private Cpu cpu;

    private Mem mem;

    private Disk disk;

}
