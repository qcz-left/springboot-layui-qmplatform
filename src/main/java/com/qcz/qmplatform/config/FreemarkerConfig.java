package com.qcz.qmplatform.config;

import com.jagregory.shiro.freemarker.ShiroTags;
import com.qcz.qmplatform.config.freemarker.MyFreeMarkerViewResolver;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.IOException;
import java.util.HashMap;

@Configuration
@Slf4j
public class FreemarkerConfig {

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() throws IOException, TemplateException {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("classpath:templates/");
        freemarker.template.Configuration configuration = freeMarkerConfigurer.createConfiguration();
        configuration.setDefaultEncoding("UTF-8");
        //这里可以添加其他共享变量 比如sso登录地址
        configuration.setSharedVariable("shiro", new ShiroTags());
        freeMarkerConfigurer.setConfiguration(configuration);
        return freeMarkerConfigurer;
    }

    @Bean
    public FreeMarkerViewResolver resolver() {
        MyFreeMarkerViewResolver resolver = new MyFreeMarkerViewResolver();
        resolver.setSuffix(".ftl");
        resolver.setContentType("text/html;charset=UTF-8");
        resolver.setRequestContextAttribute("requestContext");

        HashMap<String, Object> attributes = new HashMap<>();
        attributes.put("Json", getModel("com.qcz.qmplatform.common.utils.JSONUtils"));
        resolver.setAttributesMap(attributes);
        resolver.setOrder(0);
        return resolver;
    }


    private TemplateHashModel getModel(String packageName) {
        BeansWrapper wrapper = new BeansWrapperBuilder(freemarker.template.Configuration.VERSION_2_3_32).build();
        TemplateHashModel fileStatics;
        try {
            fileStatics = (TemplateHashModel) wrapper.getStaticModels().get(packageName);
            return fileStatics;
        } catch (TemplateModelException e) {
            log.error("", e);
        }
        return null;
    }
}
