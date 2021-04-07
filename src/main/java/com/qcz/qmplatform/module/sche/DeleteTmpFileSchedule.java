package com.qcz.qmplatform.module.sche;

import com.qcz.qmplatform.common.utils.ConfigLoader;
import com.qcz.qmplatform.common.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Objects;

/**
 * 定时删除临时文件
 */
public class DeleteTmpFileSchedule {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteTmpFileSchedule.class);

    public void run() {
        String deleteTmpPath = ConfigLoader.getDeleteTmpPath();
        File[] files = FileUtils.file(deleteTmpPath).listFiles();
        long currentTimeMillis = System.currentTimeMillis();
        for (File file : Objects.requireNonNull(files)) {
            if (currentTimeMillis - file.lastModified() > ConfigLoader.getTmpFileMaxLifeTime()) {
                FileUtils.del(file);
            }
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Delete temporary file: {}", deleteTmpPath);
        }
    }

}
