package com.qcz.qmplatform.config;

import com.qcz.qmplatform.common.utils.YmlPropertiesUtils;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SSLConfig {

    @Bean
    public TomcatServletWebServerFactory servletContainerFactory() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                //设置安全性约束
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                //设置约束条件
                SecurityCollection collection = new SecurityCollection();
                //拦截所有请求
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        //设置将分配给通过此连接器接收到的请求的方案
        connector.setScheme("http");

        //true： http使用http, https使用https;
        //false： http重定向到https;
        connector.setSecure(false);

        //设置监听请求的端口号，这个端口不能其他已经在使用的端口重复，否则会报错
        connector.setPort(YmlPropertiesUtils.getServerHttpPort());

        //重定向端口号(非SSL到SSL)
        connector.setRedirectPort(YmlPropertiesUtils.getServerPort());

        tomcat.addAdditionalTomcatConnectors(connector);
        return tomcat;
    }
}
