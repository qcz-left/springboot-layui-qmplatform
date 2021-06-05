package com.qcz.qmplatform.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.setting.Setting;

import java.util.ArrayList;
import java.util.List;

public class ConfigLoader {

    private static final Setting configFile = IniFileUtils.getConfigFile();

    private static final String GROUP_COMMON = "Common";

    private static final String GROUP_SECURE = "Secure";

    private static String getStringConfig(String group, String propertyName, String defaultValue) {
        return configFile.getStr(propertyName, group, defaultValue);
    }

    private static String getStringConfig(String propertyName, String defaultValue) {
        return configFile.getStr(propertyName, GROUP_COMMON, defaultValue);
    }

    private static String getStringConfig(String propertyName) {
        return getStringConfig(propertyName, null);
    }

    private static Long getLongConfig(String group, String propertyName, Long defaultValue) {
        return configFile.getLong(propertyName, group, defaultValue);
    }

    private static Long getLongConfig(String propertyName, Long defaultValue) {
        return configFile.getLong(propertyName, GROUP_COMMON, defaultValue);
    }

    private static Long getLongConfig(String propertyName) {
        return getLongConfig(propertyName, null);
    }

    private static Integer getIntConfig(String group, String propertyName, Integer defaultValue) {
        return configFile.getInt(propertyName, group , defaultValue);
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
     * 临时文件最大存放时间，单位：毫秒
     *
     * @return 默认 半个小时
     */
    public static Long getTmpFileMaxLifeTime() {
        return getLongConfig("TmpFileMaxLifeTime", 1800000L);
    }

    /**
     * 获取aes加密秘钥
     */
    public static String getAesKey() {
        return getStringConfig(GROUP_SECURE, "AesKey", "0123456789ABHAEQ");
    }

    /**
     * 获取des加密秘钥
     */
    public static String getDesKey() {
        return getStringConfig(GROUP_SECURE, "DesKey", "0123456789ABHAEQ");
    }

    /**
     * 获取rsa加密公钥
     */
    public static String getRsaPublicKey() {
        return getStringConfig(GROUP_SECURE, "RsaPublicKey", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2qTJMYBIn2rk8W/fRFKd9kmZUd72+tGQg5oyQL/J9x4FyNKFFbcMapzDx431HpwWns/2bHH1loxA7fojpsHCAxzA47v5nVbOmIduxLaahpcz9E0xHKwOApXwF/CVJ/5luBaRLQQYmum9WvfMet6hI/jl+XfGJuHu8k3eA3by6OwIDAQAB");
    }

    /**
     * 获取rsa加密私钥
     */
    public static String getRsaPrivateKey() {
        return getStringConfig(GROUP_SECURE, "RsaPrivateKey", "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALapMkxgEifauTxb99EUp32SZlR3vb60ZCDmjJAv8n3HgXI0oUVtwxqnMPHjfUenBaez/ZscfWWjEDt+iOmwcIDHMDju/mdVs6Yh27EtpqGlzP0TTEcrA4ClfAX8JUn/mW4FpEtBBia6b1a98x63qEj+OX5d8Ym4e7yTd4DdvLo7AgMBAAECgYA0/kp68QZjbKgAFHiFvrXHDyHEEMdKXCCOkQSn0llKV4a9IPrG30zS3z2qd8MBdjoESQpjJXVV9uR/Njs3fTpuDI9ENlsFbMcpH7T2VvTF4tfUkqJLrNG1bWy88iuHlg3s+gyoh95qj0FpQwvVNZu5y2T1ST9yJr4mNjEUKcuP1QJBAN/dNN45FOhNtDLuEO26VyHL8qWt9mmS/2V6Yv4mVop/dn0etutlW+TD7tOvEQpp8tU4yHICC81Se6zjpbuKF4UCQQDQ4dAJ3E0sHAxutwq0cKHLk47zAls+HK5CPvXz25WEY8iVkROjKXQ+nbL/kk9nEcPRKoxGOsA7yy/kE3iJnNa/AkEAvUBYrXU8Q5dNO7EfBpp9hsjP/Viv9FidKMDqZ0kp6DQRi30nzqEFqsQpZzOdCFzBOZijfB5Ws+GDRIm/VmhLsQJBANADEBnBuVhj1j2rKo3mXDlPCNwXTKXeCesOp//gfFUKyYWoo9WoQorYoJjCKzCJEGgL5wVJHIZvIZIPkcn41iECQGcPx8wUsEcx4oEr9fPQCM6nqJSP0vDm28vM0aOyQwoP8O9Oehvq7wk9GI4aY+YfzHA2S2utb/cSRp0rSmY46gc=");
    }

    public static List<String> getPwdFields() {
        String pwdFields = getStringConfig("PwdFields", "password,pwd,secretKey");
        if (StringUtils.isBlank(pwdFields)) {
            return new ArrayList<>(0);
        }
        return CollectionUtil.newArrayList(pwdFields.split(","));
    }
}
