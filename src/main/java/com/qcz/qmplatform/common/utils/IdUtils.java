package com.qcz.qmplatform.common.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class IdUtils extends IdUtil {

    private static final Snowflake SNOWFLAKE = getSnowflake(ConfigLoader.getSnowflakeWorkerId(), ConfigLoader.getSnowflakeDataCenterId());

    private IdUtils() {

    }

    /**
     * 生成一个雪花ID
     */
    public static long nextSnowflakeId() {
        return SNOWFLAKE.nextId();
    }

}
