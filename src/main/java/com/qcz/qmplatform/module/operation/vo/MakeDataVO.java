package com.qcz.qmplatform.module.operation.vo;

import com.qcz.qmplatform.module.operation.pojo.DBDetail;
import com.qcz.qmplatform.module.operation.pojo.DataDetail;

import java.io.Serializable;

public class MakeDataVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private DBDetail dbDetail;

    private DataDetail dataDetail;

    private long insertNumber;

    public long getInsertNumber() {
        return insertNumber;
    }

    public void setInsertNumber(long insertNumber) {
        this.insertNumber = insertNumber;
    }

    public DBDetail getDbDetail() {
        return dbDetail;
    }

    public void setDbDetail(DBDetail dbDetail) {
        this.dbDetail = dbDetail;
    }

    public DataDetail getDataDetail() {
        return dataDetail;
    }

    public void setDataDetail(DataDetail dataDetail) {
        this.dataDetail = dataDetail;
    }
}
