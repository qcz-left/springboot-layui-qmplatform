package com.qcz.qmplatform.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IniFileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(IniFileUtils.class);

    /**
     * config.ini配置文件路径
     */
    private static final String CONFIG_FILE_PATH = "../config.ini";

    private static IniFile configIniFile = new IniFile(CONFIG_FILE_PATH);

    /**
     * 获取config.ini配置文件实例
     *
     * @return
     */
    public static IniFile getConfigFile() {
        return configIniFile;
    }
}
