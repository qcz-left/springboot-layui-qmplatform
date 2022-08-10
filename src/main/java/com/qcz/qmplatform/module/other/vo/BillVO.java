package com.qcz.qmplatform.module.other.vo;

import com.qcz.qmplatform.module.other.domain.Bill;

public class BillVO extends Bill {

    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
