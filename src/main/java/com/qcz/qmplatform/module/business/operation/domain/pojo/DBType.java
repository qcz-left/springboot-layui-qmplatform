package com.qcz.qmplatform.module.business.operation.domain.pojo;

import com.qcz.qmplatform.common.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DBType {

    POSTGRE_SQL("postgresql", "org.postgresql.Driver", "jdbc:postgresql://{}:{}/{}"),

    MYSQL("mysql", "com.mysql.jdbc.Driver", "jdbc:mysql://{}:{}/{}"),

    ORACLE("oracle", "oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@{}:{}/{}"),

    SQL_SERVER("sqlserver", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://{}:{};databaseName={}"),

    INSTANCE("instance", "", "");

    private final String name;
    private final String driverName;
    private final String urlTemplate;

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

}
