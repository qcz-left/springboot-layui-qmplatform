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

    /**
     * 获取文件上传存放路径
     *
     * @return 文件上传路径，默认 /opt/web/file/
     */
    public static String getUploadFilePath() {
        return getStringConfig("UploadFilePath", FileUtils.WEB_PATH + "/file/");
    }

    /**
     * 获取文件下载存放路径
     *
     * @return 文件下载路径，默认 /opt/web/download/
     */
    public static String getDownloadFilePath() {
        return getStringConfig("DownloadFilePath", FileUtils.WEB_PATH + "/download/");
    }

    /**
     * 获取备份目录
     *
     * @return 备份目录，默认 /opt/web/bak/
     */
    public static String getBakPath() {
        return getStringConfig("BakPath", FileUtils.WEB_PATH + "/bak/");
    }

    /**
     * 获取数据库备份存放路径
     *
     * @return 数据库备份路径，默认 /opt/web/bak/database/
     */
    public static String getDataBakPath() {
        return getStringConfig("DataBakPath", getBakPath() + "database/");
    }

}
