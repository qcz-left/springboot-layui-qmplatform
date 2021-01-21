package com.qcz.qmplatform.module.sche;

import com.qcz.qmplatform.common.utils.ConfigLoader;
import com.qcz.qmplatform.common.utils.FileUtils;

/**
 * 定时删除临时文件
 */
public class DeleteTmpFileSchedule {

    public void run() {
        FileUtils.clean(ConfigLoader.getDeleteTmpPath());
    }

}
