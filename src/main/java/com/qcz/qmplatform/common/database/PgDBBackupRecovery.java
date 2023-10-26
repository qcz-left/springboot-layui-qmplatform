package com.qcz.qmplatform.common.database;

import com.qcz.qmplatform.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * Postgresql 数据备份与恢复
 */
@Slf4j
public class PgDBBackupRecovery implements DBBackupRecovery {

    @Override
    public String getBackupCmd(String database, String bakFilePath) {
        String backupCmd = StringUtils.format("pg_dump -U postgres -Fc {} -f {}", database, bakFilePath);
        return getFinallyBackupCmd(backupCmd);
    }

    @Override
    public String dockerName() {
        return "postgres";
    }

}
