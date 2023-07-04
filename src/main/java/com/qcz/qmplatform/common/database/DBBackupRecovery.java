package com.qcz.qmplatform.common.database;

import com.qcz.qmplatform.common.exception.BusinessException;
import com.qcz.qmplatform.common.utils.FileUtils;
import com.qcz.qmplatform.common.utils.StringUtils;

import java.io.File;

/**
 * 数据库备份与恢复接口
 * <p> 1、数据库和程序部署在同一台服务器下 </p>
 * <p> 2、本机localhost能免密登录操作数据库 </p>
 */
public interface DBBackupRecovery {

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
        if (!new File(recoverSh).exists()) {
            throw new BusinessException("备份恢复所需脚本文件缺失！");
        }
        return StringUtils.format("{} {} {} > {} 2>&1", recoverSh, bakFilePath, database, logPath);
    }

}
