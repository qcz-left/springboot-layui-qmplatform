package com.qcz.qmplatform.common.bean;

/**
 * 数据库属性定义
 */
public interface DBProperties {

    /**
     * 表名定义
     */
    interface Table {
        /**
         * 系统消息
         */
        String SYS_MESSAGE = "sys_message";
        /**
         * 第三方系统对接配置信息
         */
        String SYS_THIRDPARTY_APP = "sys_thirdparty_app";
        /**
         * 第三方系统对接配置信息
         */
        String SYS_ROLE = "sys_role";
    }

}
