package com.qcz.qmplatform.module.sche;

import com.qcz.qmplatform.common.utils.ConfigLoader;
import com.qcz.qmplatform.common.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定时删除临时文件
 */
public class DeleteTmpFileSchedule {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteTmpFileSchedule.class);

    @SuppressWarnings("ConstantConditions")
    public void run() {
        String deleteTmpPath = ConfigLoader.getDeleteTmpPath();
        LOGGER.debug("Delete temporary file: {}, length: {}", deleteTmpPath, FileUtils.file(deleteTmpPath).list().length);
        FileUtils.clean(deleteTmpPath);
    }

    public static void main(String[] args) {
        new DeleteTmpFileSchedule().run();
    }

}
