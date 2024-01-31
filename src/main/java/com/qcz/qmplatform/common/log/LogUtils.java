package com.qcz.qmplatform.common.log;

import com.qcz.qmplatform.common.constant.GroupDefine;
import com.qcz.qmplatform.common.utils.ConfigLoader;
import com.qcz.qmplatform.common.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggerConfiguration;
import org.springframework.boot.logging.LoggingSystem;

import java.util.Objects;

@Slf4j
public class LogUtils {

    /**
     * 根据配置文件重置所有日志等级
     */
    public static void resetLogLevel() {
        for (String loggerName : ConfigLoader.getPropertyNames(GroupDefine.LOG_LEVEL)) {
            resetLogLevel(loggerName);
        }
    }

    /**
     * 根据包名重置日志等级
     */
    public static void resetLogLevel(String loggerName) {
        LogLevel logLevel = ConfigLoader.getLogLevel(loggerName);
        setLogLevel(loggerName, logLevel);
    }

    /**
     * 设置指定日志等级
     */
    public static void setLogLevel(String loggerName, LogLevel logLevel) {
        LoggingSystem loggingSystem = SpringContextUtils.getBean(LoggingSystem.class);
        LoggerConfiguration loggerConfiguration = loggingSystem.getLoggerConfiguration(loggerName);
        if (Objects.isNull(loggerConfiguration) || loggerConfiguration.getConfiguredLevel() == logLevel) {
            return;
        }
        log.info("set log level: {}={}", loggerName, logLevel);
        loggingSystem.setLogLevel(loggerName, logLevel);
    }

}
