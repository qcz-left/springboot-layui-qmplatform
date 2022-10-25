package com.qcz.qmplatform.module.operation.pojo;

import com.qcz.qmplatform.common.utils.StringUtils;

public enum DBType {

    POSTGRE_SQL("postgresql", "org.postgresql.Driver", "jdbc:postgresql://{}:{}/{}"),

    MYSQL("mysql", "com.mysql.jdbc.Driver", "jdbc:mysql://{}:{}/{}"),

    ORACLE("oracle", "oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@{}:{}/{}"),

    SQL_SERVER("sqlserver", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://{}:{};databaseName={}"),

    INSTANCE("instance", "", "");

    private final String name;
    private final String driverName;
    private final String urlTemplate;

    public String getName() {
        return this.name;
    }

    public String getDriverName() {
        return this.driverName;
    }

    public String getUrlTemplate() {
        return this.urlTemplate;
    }

    public String getUrl(String host, int port, String database) {
        return StringUtils.format(urlTemplate, host, port, database);
    }

    public static DBType getDbType(String dbType) {
        DBType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            DBType type = var1[var3];
            if (type.name.equalsIgnoreCase(dbType)) {
                return type;
            }
        }

        return INSTANCE;
    }

    DBType(final String name, final String driverName, final String urlTemplate) {
        this.name = name;
        this.driverName = driverName;
        this.urlTemplate = urlTemplate;
    }
}
