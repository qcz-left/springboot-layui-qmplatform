package com.qcz.qmplatform.module.operation.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class DBDetail implements Serializable {

    private String dbName;

    private String host;

    private int port;

    private String database;

    private String user;

    private String password;

}
