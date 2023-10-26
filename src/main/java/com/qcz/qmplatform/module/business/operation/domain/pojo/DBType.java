package com.qcz.qmplatform.module.business.operation.domain.pojo;

import com.qcz.qmplatform.common.database.DBBackupRecovery;
import com.qcz.qmplatform.common.database.MySqlDBBackupRecovery;
import com.qcz.qmplatform.common.database.PgDBBackupRecovery;
import com.qcz.qmplatform.common.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DBType {

    POSTGRE_SQL("postgresql", "org.postgresql.Driver", "jdbc:postgresql://{}:{}/{}", PgDBBackupRecovery.class),

    MYSQL("mysql", "com.mysql.jdbc.Driver", "jdbc:mysql://{}:{}/{}", MySqlDBBackupRecovery.class),

    ORACLE("oracle", "oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@{}:{}/{}", DBBackupRecovery.class),

    SQL_SERVER("sqlserver", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://{}:{};databaseName={}", DBBackupRecovery.class),

    INSTANCE("instance", "", "", DBBackupRecovery.class);

    private final String name;
    private final String driverName;
    private final String urlTemplate;
    /**
     * 数据备份与恢复实现类
     */
    private final Class<? extends DBBackupRecovery> dbBackupRecoveryClass;

    public String getUrl(String host, int port, String database) {
        return StringUtils.format(urlTemplate, host, port, database);
    }

    public static DBType getDbType(String dbType) {
        DBType[] dbTypes = values();

        for (DBType type : dbTypes) {
            if (type.name.equalsIgnoreCase(dbType)) {
                return type;
            }
        }

        return INSTANCE;
    }

    public static DBType getDbTypeByDriverName(String driverName) {
        DBType[] dbTypes = values();

        for (DBType type : dbTypes) {
            if (type.driverName.equalsIgnoreCase(driverName)) {
                return type;
            }
        }

        return INSTANCE;
    }

}
