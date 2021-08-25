package com.qcz.qmplatform.common.utils;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;

/**
 * yml配置文件属性获取工具
 */
public class YmlPropertiesUtils {

    private static Properties ymlProp;

    private YmlPropertiesUtils() {

    }

    static {
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        ClassPathResource appResources = new ClassPathResource("application.yml");
        yamlPropertiesFactoryBean.setResources(appResources);
        Properties appProp = yamlPropertiesFactoryBean.getObject();
        if (appProp != null) {
            String activeProfiles = appProp.getProperty("spring.profiles.active");
            if (StringUtils.isNotBlank(activeProfiles)) {
                ClassPathResource activeResources = new ClassPathResource("application-" + activeProfiles + ".yml");
                yamlPropertiesFactoryBean.setResources(appResources, activeResources);
            }
        }
        ymlProp = yamlPropertiesFactoryBean.getObject();
    }

    /**
     * 是否开启文件预览
     */
    public static boolean enableJodConverter() {
        return Boolean.valueOf(ymlProp.getProperty("jodconverter.local.enabled"));
    }

    /**
     * 服务端口（https）
     */
    public static int getServerPort() {
        return Integer.valueOf(ymlProp.getProperty("server.port"));
    }

    /**
     * http服务端口
     */
    public static int getServerHttpPort() {
        return Integer.valueOf(ymlProp.getProperty("server.http-port"));
    }

    /**
     * 数据库名称
     */
    public static String getDatabase() {
        return ymlProp.getProperty("custom.database");
    }

    /**
     * 文件上传最大长度
     */
    public static String getMaxFileSize() {
        return ymlProp.getProperty("spring.servlet.multipart.max-file-size");
    }

}
