package com.qcz.qmplatform.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
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
        String projectPath = System.getProperty("user.dir");
        Map<OutputFile, String> pathInfo = new HashMap<>();
        String parentName = scanner("父包名（com.qcz.qmplatform.module.business 包下的）");
        String modulePrefix = parentName.replaceAll("\\.", "/") + "/";
        String moduleName = scanner("模块名");
        pathInfo.put(OutputFile.xml, projectPath + "/src/main/resources/mapper/" + modulePrefix + moduleName);

        String pageParent = projectPath + "/src/main/resources/templates/module/" + modulePrefix + moduleName + "/";
        String jsParent = projectPath + "/src/main/resources/static/module/" + modulePrefix + moduleName + "/";
        FastAutoGenerator.create("jdbc:postgresql://127.0.0.1:5432/qmplatform_single", "postgres", "postgres")
                .globalConfig(builder -> builder
                        .author("quchangzhong")
                        .outputDir(projectPath + "/src/main/java")
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig(builder -> builder
                        .parent("com.qcz.qmplatform.module.business." + parentName)
                        .pathInfo(pathInfo)
                        .entity("domain")
                        .mapper("mapper")
                        .serviceImpl("service")
                        .moduleName(moduleName)
                )
                .strategyConfig((scanner, builder) -> builder
                        .addInclude(scanner.apply("请输入表名，多个表名用,隔开"))
                        .addTablePrefix(scanner("表名前缀") + "_")
                        .entityBuilder()
                        .enableTableFieldAnnotation()
                        .enableLombok()
                        .enableChainModel()
                        .enableFileOverride()
                        .controllerBuilder()
                        .template("/ftl/controller.java")
                        .serviceBuilder()
                        .disableService()
                        .serviceImplTemplate("/ftl/service.java")
                        .convertServiceImplFileName(entityName -> entityName + "Service")
                )
                .injectionConfig((scanner, builder) -> {
                    List<CustomFile> customFiles = new ArrayList<>();
                    String scanPage = scanner.apply("是否输出页面文件 Y/N");
                    if (StringUtils.equalsIgnoreCase(scanPage, "Y")) {
                        customFiles.add(new CustomFile.Builder()
                                .templatePath("/ftl/list.ftl.ftl")
                                .formatNameFunction(tableInfo -> {
                                    // 自定义输出文件名
                                    return pageParent + StringUtils.lowerFirst(tableInfo.getEntityName()) + "List.ftl";
                                }).build());
                        customFiles.add(new CustomFile.Builder()
                                .templatePath("/ftl/detail.ftl.ftl")
                                .formatNameFunction(tableInfo -> {
                                    // 自定义输出文件名
                                    return pageParent + StringUtils.lowerFirst(tableInfo.getEntityName()) + "Detail.ftl";
                                }).build());
                        customFiles.add(new CustomFile.Builder()
                                .templatePath("/ftl/list.js.ftl")
                                .formatNameFunction(tableInfo -> {
                                    // 自定义输出文件名
                                    return jsParent + StringUtils.lowerFirst(tableInfo.getEntityName()) + ".js";
                                }).build());
                    }
                    builder.customFile(customFiles);
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

}
