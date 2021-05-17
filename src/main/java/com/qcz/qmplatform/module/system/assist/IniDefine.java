package com.qcz.qmplatform.module.system.assist;

/**
 * sys_ini属性定义
 */
public interface IniDefine {

    /**
     * 数据备份与恢复
     */
    String DATA_BAK = "DataBak";

    interface DataBak {
        /**
         * 开启备份（0：关闭，1：开启）
         */
        String ENABLE_BAK = "EnableBak";
        /**
         * 周期
         */
        String PERIOD = "Period";
        /**
         * 磁盘空间限制多少时不允许备份
         */
        String LIMIT_DISK_SPACE = "LimitDiskSpace";
        /**
         * 保存的备份天数
         */
        String SAVE_DAYS = "SaveDays";
    }

}
