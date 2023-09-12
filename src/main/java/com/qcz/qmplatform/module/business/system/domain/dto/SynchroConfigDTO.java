package com.qcz.qmplatform.module.business.system.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 同步配置参数
 */
@Data
@Accessors(chain = true)
public class SynchroConfigDTO implements Serializable {

    /**
     * 启用开关（0：关闭；2：开启）
     */
    private int enableSynchro;

    /**
     * 同步类型（dingtalk-synchro：钉钉; work-wechat-synchro：企业微信）
     */
    private String syncType;

    /**
     * 登录名同步方式
     */
    private String loginNameSyncWay;

    /**
     * 同步周期
     */
    private String syncPeriod;

    /**
     * 同步日期
     */
    private String syncDate;

    /**
     * 同步时间
     */
    private String syncTime;
}
