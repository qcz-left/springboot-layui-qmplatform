package com.qcz.qmplatform.config;

import com.jagregory.shiro.freemarker.ShiroTags;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;

@Configuration
@Slf4j
public class FreemarkerConfig implements InitializingBean {

    @Resource
    private FreeMarkerViewResolver resolver;

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

    @Override
    public void afterPropertiesSet() {
        resolver.setRequestContextAttribute("requestContext");
        HashMap<String, Object> attributes = new HashMap<>();
        attributes.put("Json", getModel("com.qcz.qmplatform.common.utils.JSONUtils"));
        resolver.setAttributesMap(attributes);
    }

    private TemplateHashModel getModel(String packageName) {
        BeansWrapper wrapper = new BeansWrapperBuilder(freemarker.template.Configuration.VERSION_2_3_28).build();
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
