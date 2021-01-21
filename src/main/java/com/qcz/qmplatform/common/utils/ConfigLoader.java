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
     * 获取临时目录
     *
     * @return 默认 /opt/web/tmp/
     */
    public static String getTmpPath() {
        return getStringConfig("TmpPath", FileUtils.WEB_PATH + "/tmp/");
    }

    /**
     * 获取可删除的临时目录
     *
     * @return 默认 /opt/web/tmp/delete/
     */
    public static String getDeleteTmpPath() {
        return getStringConfig("DeleteTmpPath", getTmpPath() + "delete/");
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

    /**
     * 跳转首页选项卡tab最大显示数
     *
     * @return tab最大显示数，默认 10
     */
    public static Integer getMaxTabs() {
        return getIntConfig("MaxTabs", 10);
    }

}
