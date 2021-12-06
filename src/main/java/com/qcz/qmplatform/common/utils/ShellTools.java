package com.qcz.qmplatform.common.utils;

import cn.hutool.core.util.RuntimeUtil;
import com.qcz.qmplatform.common.exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ShellTools {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShellTools.class);

    /**
     * 数据库备份
     *
     * @param database    数据库名称
     * @param bakFilePath 备份路径
     */
    public static void databaseDump(String database, String bakFilePath) {
        String dumpCmd = StringUtils.format("pg_dump -U postgres -Fc {} -f {}", database, bakFilePath);
        LOGGER.debug("dump exe shell: " + dumpCmd);
        LOGGER.debug(RuntimeUtil.execForStr(dumpCmd));
    }

    /**
     * 数据库还原
     *
     * @param database    数据库名称
     * @param bakFilePath 备份路径
     * @param logPath     日志文件目录
     */
    public static void databaseRecover(String database, String bakFilePath, String logPath) {
        String recoverSh = FileUtils.WEB_PATH + "/shell/db_bak_recover.sh";
        if (!new File(recoverSh).exists()) {
            throw new CommonException("备份恢复所需脚本文件缺失！");
        }
        FileUtils.touch(logPath);
        String exeSh = StringUtils.format("{} {} {} > {} 2>&1", recoverSh, bakFilePath, database, logPath);
        LOGGER.debug("recover exe shell: " + exeSh);
        ThreadPoolUtils.execute(() -> RuntimeUtil.exec(exeSh));
    }

    private ShellTools() {

    }
}
