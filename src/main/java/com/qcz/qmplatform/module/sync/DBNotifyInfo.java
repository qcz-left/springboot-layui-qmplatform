package com.qcz.qmplatform.module.sync;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DBNotifyInfo {

    private String tableName;

    private Object msg;

    public static DBNotifyInfo newInstance(String tableName, Object msg) {
        return new DBNotifyInfo(tableName, msg);
    }

}
