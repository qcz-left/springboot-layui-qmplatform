package com.qcz.qmplatform.module.sync;

public class DBNotifyInfo {

    private String tableName;

    private Object msg;

    public DBNotifyInfo() {

    }

    public DBNotifyInfo(String tableName, Object msg) {
        this.tableName = tableName;
        this.msg = msg;
    }

    public static DBNotifyInfo newInstance(String tableName, Object msg) {
        return new DBNotifyInfo(tableName, msg);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }
}
