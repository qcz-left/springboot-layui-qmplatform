package com.qcz.qmplatform.config;

import cn.hutool.core.thread.ThreadUtil;
import com.qcz.qmplatform.common.utils.CloseUtils;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

@Configuration
@Slf4j
public class DataSourceConfig {

    @Resource
    private HikariDataSource dataSource;

    @PostConstruct
    public void init() throws SQLException {
        Connection connection = null;
        int count = 0;
        while (true) {
            try {
                connection = dataSource.getConnection();
                if (Objects.nonNull(connection)) {
                    log.debug("database connection check success.");
                    break;
                }
            } catch (Throwable e) {
                log.error("[{}]database connection failed, and will be attempted again! \n {}: {}", count, e.getCause(), e.getMessage());
            } finally {
                CloseUtils.close(connection);
            }

            count++;
            ThreadUtil.sleep(10000L);
        }

    }

    @Bean
    public DatabaseIdProvider getDatabaseIdProvider() {
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties p = new Properties();
        p.setProperty("PostgreSQL", "pg");
        p.setProperty("MySQL", "mysql");
        databaseIdProvider.setProperties(p);
        return databaseIdProvider;
    }

}
