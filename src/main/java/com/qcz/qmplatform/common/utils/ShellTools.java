package com.qcz.qmplatform.common.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.RuntimeUtil;
import com.qcz.qmplatform.module.business.operation.domain.pojo.DBType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * shell命令工具类
 */
public class ShellTools {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShellTools.class);

    /**
     * 数据库备份
     *
     * @param database    数据库名称
     * @param bakFilePath 备份路径
     */
    public static void databaseDump(String database, String bakFilePath) {
        DBType dbType = DBType.getDbTypeByDriverName(YmlPropertiesUtils.getDatasourceDriverName());
        String dumpCmd = ReflectUtil.newInstance(dbType.getDbBackupRecoveryClass()).getBackupCmd(database, bakFilePath);
        LOGGER.debug("dump exe shell: " + dumpCmd);
        LOGGER.debug(execForStr(dumpCmd));
    }

    /**
     * 数据库还原命令加入缓存，等待执行
     *
     * @return 命令id
     */
    public static String databaseRecoverToCache(String database, String bakFilePath, String logPath) {
        String cmdId = IdUtils.getUUID();
        DBType dbType = DBType.getDbTypeByDriverName(YmlPropertiesUtils.getDatasourceDriverName());
        CacheUtils.putCmd(cmdId, ReflectUtil.newInstance(dbType.getDbBackupRecoveryClass()).getRecoveryCmd(database, bakFilePath, logPath));
        return cmdId;
    }

    public static Process exec(String... cmd) {
        List<String> cmdList = new ArrayList<>();
        cmdList.add("/bin/sh");
        cmdList.add("-c");
        cmdList.addAll(Arrays.asList(cmd));
        return RuntimeUtil.exec(ArrayUtil.toArray(cmdList, String.class));
    }

    public static String execForStr(String... cmd) {
        List<String> cmdList = new ArrayList<>();
        cmdList.add("/bin/sh");
        cmdList.add("-c");
        cmdList.addAll(Arrays.asList(cmd));
        return RuntimeUtil.execForStr(ArrayUtil.toArray(cmdList, String.class));
    }

    private ShellTools() {

    }
}
