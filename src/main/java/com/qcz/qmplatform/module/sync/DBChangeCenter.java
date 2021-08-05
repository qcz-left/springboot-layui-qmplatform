package com.qcz.qmplatform.module.sync;

import cn.hutool.core.thread.ThreadUtil;
import com.qcz.qmplatform.common.bean.Observed;

/**
 * 数据库改变
 */
public class DBChangeCenter implements Observed {

    public static DBChangeCenter getInstance() {
        return DBChangeCenterHolder.INSTANCE;
    }

    private DBChangeCenter() {

    }

    private static class DBChangeCenterHolder {
        private static final DBChangeCenter INSTANCE = new DBChangeCenter();

        static {
            INSTANCE.addObserver(new DBChangeWatcher());
            ThreadUtil.execute(INSTANCE);
        }

    }

}