package com.qcz.qmplatform.common.utils;

import cn.hutool.core.util.ArrayUtil;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * yml配置文件属性获取工具
 */
public class YmlPropertiesUtils {

    private static final Properties YML_PROP;

    private YmlPropertiesUtils() {

    }

    static {
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        List<ClassPathResource> classPathResources = new ArrayList<>();
        ClassPathResource appResources = new ClassPathResource("application.yml");
        classPathResources.add(appResources);

        yamlPropertiesFactoryBean.setResources(appResources);
        Properties appProp = yamlPropertiesFactoryBean.getObject();
        if (appProp != null) {
            String activeProfiles = appProp.getProperty("spring.profiles.active");
            if (StringUtils.isNotBlank(activeProfiles)) {
                for (String activeProfile : StringUtils.split(activeProfiles, ",")) {
                    ClassPathResource activeResource = new ClassPathResource("application-" + activeProfile + ".yml");
                    if (!activeResource.exists()) {
                        continue;
                    }
                    classPathResources.add(activeResource);
                }
            }
        }
        yamlPropertiesFactoryBean.setResources(ArrayUtil.toArray(classPathResources, ClassPathResource.class));
        YML_PROP = yamlPropertiesFactoryBean.getObject();
    }

    /**
     * 是否开启文件预览
     */
    public static boolean enableJodConverter() {
        return Boolean.parseBoolean(YML_PROP.getProperty("jodconverter.local.enabled"));
    }

    /**
     * 服务端口（https）
     */
    public static int getServerPort() {
        return Integer.parseInt(YML_PROP.getProperty("server.port"));
    }

    /**
     * http服务端口
     */
    public static int getServerHttpPort() {
        return Integer.parseInt(YML_PROP.getProperty("server.http-port"));
    }

    /**
     * 数据库名称
     */
    public static String getDatabase() {
        return YML_PROP.getProperty("custom.database");
    }

    /**
     * 文件上传最大长度
     */
    public static String getMaxFileSize() {
        return YML_PROP.getProperty("spring.servlet.multipart.max-file-size");
    }

    /**
     * 是否开启http重定向到https
     */
    public static boolean redirectHttps() {
        return Boolean.parseBoolean(YML_PROP.getProperty("custom.redirect-https", Boolean.FALSE.toString()));
    }

    /**
     * 打包方式（jar或war）
     */
    public static String getPacking() {
        return YML_PROP.getProperty("custom.packing", "jar");
    }

    /**
     * 数据库是否docker环境
     */
    public static boolean dbIsDocker() {
        return Boolean.parseBoolean(YML_PROP.getProperty("custom.dbIsDocker", Boolean.FALSE.toString()));
    }

    /**
     * 获取数据库驱动类名
     */
    public static String getDatasourceDriverName() {
        return YML_PROP.getProperty("spring.datasource.driver-class-name");
    }

}
