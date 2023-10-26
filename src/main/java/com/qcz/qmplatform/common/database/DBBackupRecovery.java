package com.qcz.qmplatform.common.database;

import cn.hutool.core.lang.Assert;
import com.qcz.qmplatform.common.exception.BusinessException;
import com.qcz.qmplatform.common.utils.FileUtils;
import com.qcz.qmplatform.common.utils.StringUtils;
import com.qcz.qmplatform.common.utils.YmlPropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 数据库备份与恢复接口
 * <p> 1、数据库和程序部署在同一台服务器下 </p>
 * <p> 2、本机localhost能免密登录操作数据库 </p>
 * <p> 3、docker环境下bak/shell/logs文件夹需映射到容器 </p>
 */
public interface DBBackupRecovery {

    Logger LOGGER = LoggerFactory.getLogger(DBBackupRecovery.class);

    boolean DB_IS_DOCKER = YmlPropertiesUtils.dbIsDocker();

    /**
     * 获取备份命令
     *
     * @param database    数据库名称
     * @param bakFilePath 备份路径
     */
    String getBackupCmd(String database, String bakFilePath);

    /**
     * 获取恢复备份命令
     *
     * @param database    数据库名称
     * @param bakFilePath 备份路径
     * @param logPath     日志文件路径
     * @return 缓存命令ID
     */
    default String getRecoveryCmd(String database, String bakFilePath, String logPath) {
        String recoverSh = FileUtils.WEB_PATH + "/shell/db_bak_recover.sh";
        File recoveryFile = new File(recoverSh);
        if (!recoveryFile.exists()) {
            throw new BusinessException("备份恢复所需脚本文件缺失！");
        }
        if (!recoveryFile.canExecute()) {
            boolean executable = recoveryFile.setExecutable(true);
            LOGGER.info("{} is can not execute, and set it can execute: {}", recoverSh, executable);
        }
        String recoveryCmd = StringUtils.format("{} {} {} > {} 2>&1", recoverSh, bakFilePath, database, logPath);
        if (DB_IS_DOCKER) {
            recoveryCmd = StringUtils.format(dockerShellFormat(), recoveryCmd);
        }
        return recoveryCmd;
    }

    /**
     * docker环境下docker执行命令脚本模板
     */
    default String dockerShellFormat() {
        String dockerName = dockerName();
        if (DB_IS_DOCKER) {
            Assert.notBlank(dockerName);
            return "docker exec -i " + dockerName + " /bin/bash -c '{}'";
        }
        return "{}";
    }

    /**
     * docker环境下docker名称
     */
    default String dockerName() {
        return "";
    }

    /**
     * 获取最终的备份命令
     */
    default String getFinallyBackupCmd(String backupCmd) {
        if (DB_IS_DOCKER) {
            backupCmd = StringUtils.format(dockerShellFormat(), backupCmd);
        }
        return backupCmd;
    }

}
