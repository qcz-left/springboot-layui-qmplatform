package com.qcz.qmplatform.config;

import cn.dev33.satoken.config.SaTokenConfig;
import com.qcz.qmplatform.common.utils.ConfigLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MySaTokenConfig {

    @Bean
    @Primary
    public SaTokenConfig getSaTokenConfigPrimary() {
        SaTokenConfig config = new SaTokenConfig();
        int sessionTimeout = ConfigLoader.getSessionTimeout();
        config.setTimeout(-1);                      // token 有效期（单位：秒），默认30天，-1代表永不过期
        config.setActiveTimeout(sessionTimeout);    // token 最低活跃频率（单位：秒），如果 token 超过此时间没有访问系统就会被冻结，默认-1 代表不限制，永不冻结
        config.setIsLog(true);                      // 是否输出操作日志
        return config;
    }

}
