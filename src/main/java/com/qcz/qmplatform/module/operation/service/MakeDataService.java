package com.qcz.qmplatform.module.operation.service;

import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.SqlConnRunner;
import com.qcz.qmplatform.common.utils.CloseUtils;
import com.qcz.qmplatform.common.utils.ThreadPoolUtils;
import com.qcz.qmplatform.module.operation.pojo.DBDetail;
import com.qcz.qmplatform.module.operation.pojo.DBType;
import com.qcz.qmplatform.module.operation.pojo.DataDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

@Service
public class MakeDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MakeDataService.class);

    public void start(DBDetail dbDetail, DataDetail dataDetail) {
        ThreadPoolUtils.execute(() -> {
            Connection connection = null;
            try {
                connection = getConnection(dbDetail);
                SqlConnRunner sqlConnRunner = DbUtil.newSqlConnRunner(connection);

                Entity entity = new Entity();
                entity.setTableName(dataDetail.getTableName());

                List<DataDetail.ColumnDetail> columnDetails = dataDetail.getColumnDetails();
                for (DataDetail.ColumnDetail columnDetail : columnDetails) {
                    entity.put(columnDetail.getName(), columnDetail.getValue());
                }

                sqlConnRunner.insert(connection, entity);
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

}
