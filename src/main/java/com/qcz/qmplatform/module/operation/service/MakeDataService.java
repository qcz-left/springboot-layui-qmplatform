package com.qcz.qmplatform.module.operation.service;

import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.SqlConnRunner;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.Table;
import com.qcz.qmplatform.common.utils.CloseUtils;
import com.qcz.qmplatform.common.utils.MetaUtils;
import com.qcz.qmplatform.common.utils.ThreadPoolUtils;
import com.qcz.qmplatform.module.operation.domain.pojo.DBDetail;
import com.qcz.qmplatform.module.operation.domain.pojo.DBType;
import com.qcz.qmplatform.module.operation.domain.pojo.DataDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Service
public class MakeDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MakeDataService.class);

    public void start(DBDetail dbDetail, DataDetail dataDetail, long insertNumber) {
        ThreadPoolUtils.execute(() -> {
            Connection connection = null;
            try {
                connection = getConnection(dbDetail);
                SqlConnRunner sqlConnRunner = DbUtil.newSqlConnRunner(connection);

                List<Entity> entities = new ArrayList<>();

                LOGGER.info("start sorting data...");
                for (int i = 0; i < insertNumber; i++) {
                    Entity entity = new Entity();
                    entity.setTableName(dataDetail.getTableName().toLowerCase());

                    List<DataDetail.ColumnDetail> columnDetails = dataDetail.getColumnDetails();
                    for (DataDetail.ColumnDetail columnDetail : columnDetails) {
                        entity.put(columnDetail.getName(), columnDetail.getFinallyValue());
                    }
                    entities.add(entity);
                }
                LOGGER.info("end sorting data. and start insert data...");
                sqlConnRunner.insert(connection, entities);
                LOGGER.info("end insert data");
            } catch (Exception e) {
                LOGGER.error("", e);
            } finally {
                CloseUtils.close(connection);
            }
        });
    }

    public static void main(String[] args) throws Exception {
        DBDetail dbDetail = new DBDetail();
        dbDetail.setDbName("postgresql");
        dbDetail.setDatabase("unimondb");
        dbDetail.setHost("192.168.81.236");
        dbDetail.setPort(5432);
        dbDetail.setUser("wwhlzzf");
        dbDetail.setPassword("zcrlylw");
        Connection connection = getConnection(dbDetail);
        System.out.println(connection);

        Table tableMeta = MetaUtils.getTableMeta(connection, "tbl_ini");
        for (Column column : tableMeta.getColumns()) {
            System.out.println(column);
        }

        CloseUtils.close(connection);
    }

    public boolean testConnect(DBDetail dbDetail) {
        Connection connection = null;
        try {
            connection = getConnection(dbDetail);
            return true;
        } catch (Exception e) {
            LOGGER.warn("test connect failed!", e);
        } finally {
            CloseUtils.close(connection);
        }

        return false;
    }

    public static Connection getConnection(DBDetail dbDetail) throws Exception {
        DBType dbType = DBType.getDbType(dbDetail.getDbName());
        Class.forName(dbType.getDriverName());
        return DriverManager.getConnection(dbType.getUrl(dbDetail.getHost(), dbDetail.getPort(), dbDetail.getDatabase()), dbDetail.getUser(), dbDetail.getPassword());
    }

    public List<DataDetail.ColumnDetail> getColumnList(DBDetail dbDetail, String tableName) {
        List<DataDetail.ColumnDetail> columnDetails = new ArrayList<>();
        try {
            Connection connection = getConnection(dbDetail);
            Table tableMeta = MetaUtils.getTableMeta(connection, tableName);
            for (Column column : tableMeta.getColumns()) {
                DataDetail.ColumnDetail columnDetail = new DataDetail.ColumnDetail();
                columnDetail.setName(column.getName());
                int type = column.getType();
                if (type == Types.INTEGER
                        || type == Types.BIGINT
                        || type == Types.SMALLINT
                        || type == Types.TINYINT) {
                    columnDetail.setType("number");
                    columnDetail.setLength(2);
                } else if (type == Types.DATE
                        || type == Types.TIME
                        || type == Types.TIME_WITH_TIMEZONE
                        || type == Types.TIMESTAMP
                        || type == Types.TIMESTAMP_WITH_TIMEZONE) {
                    columnDetail.setType("date");
                } else {
                    columnDetail.setType("string");
                    columnDetail.setLength(10);
                }
                columnDetail.setValueType(2);

                columnDetails.add(columnDetail);
            }
        } catch (Exception e) {
            LOGGER.warn("test connect failed!", e);
        }
        return columnDetails;
    }
}
