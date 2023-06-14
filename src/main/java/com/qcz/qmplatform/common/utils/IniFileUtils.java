package com.qcz.qmplatform.common.utils;

import cn.hutool.setting.Setting;

/**
 * INI 配置文件工具类
 */
public class IniFileUtils {

    /**
     * config.ini配置文件路径
     */
    private static final String CONFIG_FILE_PATH = FileUtils.WEB_PATH + "/config/config.ini";

    private static final Setting configIniFile;

    static {
        FileUtils.createIfNotExists(CONFIG_FILE_PATH);
        configIniFile = new Setting(CONFIG_FILE_PATH);
        configIniFile.autoLoad(true);
    }

    /**
     * 获取config.ini配置文件实例
     */
    public static Setting getConfigFile() {
        return configIniFile;
    }

}
