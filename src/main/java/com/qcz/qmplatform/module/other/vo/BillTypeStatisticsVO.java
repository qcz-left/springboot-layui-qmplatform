package com.qcz.qmplatform.module.other.vo;

import java.math.BigDecimal;

/**
 * 账单类型统计
 */
public class BillTypeStatisticsVO {

    /**
     * 账单类型名称
     */
    private String typeName;

    /**
     * 金额
     */
    private BigDecimal amount;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
