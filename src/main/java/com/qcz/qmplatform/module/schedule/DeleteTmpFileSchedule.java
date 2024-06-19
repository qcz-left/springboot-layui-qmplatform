package com.qcz.qmplatform.module.schedule;

import com.qcz.qmplatform.common.utils.CollectionUtils;
import com.qcz.qmplatform.common.utils.ConfigLoader;
import com.qcz.qmplatform.common.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 定时删除临时文件
 */
public class DeleteTmpFileSchedule {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteTmpFileSchedule.class);

    public void run() {
        String deleteTmpPath = ConfigLoader.getDeleteTmpPath();
        FileUtils.createDirIfNotExists(deleteTmpPath);
        File[] files = FileUtils.file(deleteTmpPath).listFiles();
        long currentTimeMillis = System.currentTimeMillis();
        Long tmpFileMaxLifeTime = ConfigLoader.getTmpFileMaxLifeTime();
        // 需要删除的文件
        List<String> delFilePaths = new ArrayList<>();
        for (File file : Objects.requireNonNull(files)) {
            if (currentTimeMillis - file.lastModified() > tmpFileMaxLifeTime) {
                delFilePaths.add(file.getName());
                FileUtils.del(file);
            }
        }
        LOGGER.debug("Delete temporary file dir: {}, maximum file life: {} minutes, file details: \n{}",
                deleteTmpPath,
                tmpFileMaxLifeTime / 60000,
                CollectionUtils.join(delFilePaths, ","));
    }

}
