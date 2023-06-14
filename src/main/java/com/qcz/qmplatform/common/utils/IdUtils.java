package com.qcz.qmplatform.common.utils;

import cn.hutool.core.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * ID获取工具类
 */
@Component
public class IdUtils extends IdUtil implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdUtils.class);

    private IdUtils() {

    }

    public static String getUUID() {
        return fastSimpleUUID();
    }

    @Override
    public void afterPropertiesSet() {
        LOGGER.debug("Initializing IdUtils: {}", getUUID());
    }
}
