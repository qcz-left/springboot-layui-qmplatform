package com.qcz.qmplatform.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigLoader {

    private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);

    private static final IniFile configFile = IniFileUtils.getConfigFile();

    private static final String SECTION_COMMON = "Common";

    private static String getStringConfig(String propertyName, String defaultValue) {
        return configFile.getStringProperty(SECTION_COMMON, propertyName, defaultValue);
    }

    private static String getStringConfig(String propertyName) {
        return getStringConfig(propertyName, null);
    }

    private static Long getLongConfig(String propertyName, Long defaultValue) {
        return configFile.getLongProperty(SECTION_COMMON, propertyName, defaultValue);
    }

    private static Long getLongConfig(String propertyName) {
        return getLongConfig(propertyName, null);
    }

    private static Integer getIntConfig(String propertyName, Integer defaultValue) {
        return configFile.getIntegerProperty(SECTION_COMMON, propertyName, defaultValue);
    }

    private static Integer getIntConfig(String propertyName) {
        return getIntConfig(propertyName, null);
    }

    public static String getUploadFilePath() {
        return getStringConfig("UploadFilePath", "/opt/web/file");
    }

}
