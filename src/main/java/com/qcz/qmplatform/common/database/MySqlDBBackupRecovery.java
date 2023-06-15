package com.qcz.qmplatform.common.database;

import com.qcz.qmplatform.common.utils.StringUtils;

/**
 * MySql 数据备份与恢复
 */
public class MySqlDBBackupRecovery implements DBBackupRecovery {

    @Override
    public String getBackupCmd(String database, String bakFilePath) {
        return StringUtils.format("mysqldump -u root {} > {}", database, bakFilePath);
    }

}
