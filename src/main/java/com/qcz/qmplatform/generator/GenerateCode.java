package com.qcz.qmplatform.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.qcz.qmplatform.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GenerateCode {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    private static String scanner(String tip) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("quchangzhong");
        gc.setOpen(false);
        gc.setServiceImplName("%sService");
        gc.setDateType(DateType.TIME_PACK);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:postgresql://127.0.0.1:5432/qmplatform_single");
        dsc.setDriverName("org.postgresql.Driver");
        dsc.setUsername("postgres");
        dsc.setPassword("postgres");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.qcz.qmplatform.module");
        pc.setEntity("domain");
        Map<String, String> pathInfo = new HashMap<>();
        String parent = projectPath + "/src/main/java/com/qcz/qmplatform/module/" + pc.getModuleName() + "/";
        pathInfo.put("xml_path", projectPath + "/src/main/resources/mapper/" + pc.getModuleName());
        pathInfo.put("entity_path", parent + pc.getEntity());
        pathInfo.put("mapper_path", parent + pc.getMapper());
        pathInfo.put("controller_path", parent + pc.getController());
        pc.setPathInfo(pathInfo);
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(false);
        strategy.setRestControllerStyle(false);
        strategy.setSuperControllerClass("com.qcz.qmplatform.module.base.BaseController");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(scanner("表名前缀") + "_");
        strategy.setEntityTableFieldAnnotationEnable(true);
        mpg.setStrategy(strategy);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig("/ftl/controller.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return parent + "controller/" + tableInfo.getEntityName() + "Controller" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig("/ftl/service.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return parent + "service/" + tableInfo.getEntityName() + "Service" + StringPool.DOT_JAVA;
            }
        });

        String pageParent = projectPath + "/src/main/resources/templates/module/" + pc.getModuleName() + "/";
        String jsParent = projectPath + "/src/main/resources/static/module/" + pc.getModuleName() + "/";
        String scanPage = scanner("是否输出页面文件 Y/N");
        if (StringUtils.equalsIgnoreCase(scanPage, "Y")) {
            // 前端文件
            focList.add(new FileOutConfig("/ftl/list.ftl.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return pageParent + StringUtils.lowerFirst(tableInfo.getEntityName()) + "List.ftl";
                }
            });
            focList.add(new FileOutConfig("/ftl/detail.ftl.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return pageParent + StringUtils.lowerFirst(tableInfo.getEntityName()) + "Detail.ftl";
                }
            });
            focList.add(new FileOutConfig("/ftl/list.js.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    return jsParent + StringUtils.lowerFirst(tableInfo.getEntityName()) + "List.js";
                }
            });
        }

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        mpg.execute();
    }
}
