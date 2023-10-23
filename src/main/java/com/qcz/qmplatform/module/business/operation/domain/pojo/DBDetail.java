package com.qcz.qmplatform.module.business.operation.domain.pojo;

import com.qcz.qmplatform.common.validation.IpAddress;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class DBDetail implements Serializable {

    private String dbName;

    @IpAddress
    private String host;

    private int port;

    private String database;

    private String user;

    private String password;

}
