package com.qcz.qmplatform.module.business.operation.domain.vo;

import com.qcz.qmplatform.module.business.operation.domain.pojo.DataBakStrategy;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class DataBakStrategyVO extends DataBakStrategy {

    private int week1;
    private int week2;
    private int week3;
    private int week4;
    private int week5;
    private int week6;
    private int week7;

}
