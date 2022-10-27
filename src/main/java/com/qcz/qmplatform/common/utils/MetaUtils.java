package com.qcz.qmplatform.common.utils;

import cn.hutool.db.DbRuntimeException;
import cn.hutool.db.DbUtil;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import cn.hutool.db.meta.TableType;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MetaUtils extends MetaUtil {

    public static Table getTableMeta(Connection conn, String tableName) {
        final Table table = Table.create(tableName);
        ResultSet rs = null;
        try {
            // catalog和schema获取失败默认使用null代替
            String catalog = null;
            try {
                catalog = conn.getCatalog();
            } catch (SQLException e) {
                // ignore
            }
            String schema = null;
            try {
                schema = conn.getSchema();
            } catch (SQLException e) {
                // ignore
            }

            final DatabaseMetaData metaData = conn.getMetaData();

            // 获得表元数据（表注释）
            rs = metaData.getTables(catalog, schema, tableName, new String[]{TableType.TABLE.value()});
            if (rs.next()) {
                table.setComment(rs.getString("REMARKS"));
            }

            // 获得主键
            rs = metaData.getPrimaryKeys(catalog, schema, tableName);
            while (rs.next()) {
                table.addPk(rs.getString("COLUMN_NAME"));
            }

            // 获得列
            rs = metaData.getColumns(catalog, schema, tableName, null);
            while (rs.next()) {
                table.setColumn(Column.create(tableName, rs));
            }
        } catch (SQLException e) {
            throw new DbRuntimeException("Get columns error!", e);
        } finally {
            DbUtil.close(rs, conn);
        }

        return table;
    }
}
