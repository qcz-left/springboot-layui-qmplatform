package com.qcz.qmplatform.common.utils;

import cn.hutool.setting.Setting;

public class ConfigLoader {

    private static final Setting configFile = IniFileUtils.getConfigFile();

    private static final String GROUP_COMMON = "Common";

    private static String getStringConfig(String propertyName, String defaultValue) {
        return configFile.getStr(propertyName, GROUP_COMMON, defaultValue);
    }

    private static String getStringConfig(String propertyName) {
        return getStringConfig(propertyName, null);
    }

    private static Long getLongConfig(String propertyName, Long defaultValue) {
        return configFile.getLong(propertyName, GROUP_COMMON, defaultValue);
    }

    private static Long getLongConfig(String propertyName) {
        return getLongConfig(propertyName, null);
    }

    private static Integer getIntConfig(String propertyName, Integer defaultValue) {
        return configFile.getInt(propertyName, GROUP_COMMON, defaultValue);
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

    /**
     * 临时文件最大存放时间
     *
     * @return 默认 半个小时
     */
    public static Long getTmpFileMaxLifeTime() {
        return getLongConfig("TmpFileMaxLifeTime", 1800000L);
    }

}
