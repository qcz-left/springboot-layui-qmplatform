package com.qcz.qmplatform.common.utils;

import cn.hutool.core.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class IdUtils extends IdUtil implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdUtils.class);

    private IdUtils() {

    }

    @Override
    public void afterPropertiesSet() {
        LOGGER.debug("Initializing IdUtils: {}", simpleUUID());
    }
}
