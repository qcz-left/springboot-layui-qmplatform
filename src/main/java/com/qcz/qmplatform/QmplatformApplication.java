package com.qcz.qmplatform;

import com.qcz.qmplatform.common.utils.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class QmplatformApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(QmplatformApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(QmplatformApplication.class);
    }

}