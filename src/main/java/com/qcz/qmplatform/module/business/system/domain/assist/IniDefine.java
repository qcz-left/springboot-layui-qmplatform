package com.qcz.qmplatform.module.business.system.domain.assist;

/**
 * sys_ini属性定义
 */
public interface IniDefine {

    /**
     * 数据备份与恢复
     */
    String DATA_BAK = "DataBak";

    /**
     * 登录策略
     */
    String LOGIN_STRATEGY = "LoginStrategy";

    /**
     * 登录设置
     */
    String LOGIN_SETTING = "LoginSetting";

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

    interface LoginStrategy {
        /**
         * 是否开启（0：关闭，1：开启）
         */
        String ENABLE = "Enable";
        /**
         * 登录错误超过多少次时需要填写验证码
         */
        String CODE_AT_ERROR_TIMES = "CodeAtErrorTimes";

        /**
         * 密码认证失败超过多少次时锁定账号
         */
        String LOCK_AT_ERROR_TIMES = "LockAtErrorTimes";
    }

    interface LoginSetting {
        /**
         * 其它登录方式
         */
        String OTHER_LOGIN_WAY = "otherLoginWay";
    }
}
