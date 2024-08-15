package com.qcz.qmplatform.module.business.operation.domain.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 数据备份策略
 */
@Data
@Accessors(chain = true)
public class DataBakStrategy implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 备份周期（1：星期一，2：星期二，:4：星期三，8：星期四，16：星期五，32：星期六，64：星期天）按位取值相加
     */
    private int period;

    /**
     * 剩余磁盘大小多少才进行备份（G）
     */
    private int limitDiskSpace = 20;

    /**
     * 备份开关（0：关；1：开）
     */
    private int enable;

    /**
     * 备份保存天数
     */
    private int saveDays = 30;

}
