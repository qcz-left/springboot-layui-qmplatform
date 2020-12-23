package com.qcz.qmplatform.common.utils;

import cn.hutool.core.io.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileUtils extends FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 将对象写到文件
     *
     * @param obj
     * @param filePath
     */
    public static void writeObjectToFile(Object obj, String filePath) {
        writeObjectToFile(obj, new File(filePath));
    }

    /**
     * 将对象写到文件
     *
     * @param obj
     * @param file
     */
    public static void writeObjectToFile(Object obj, File file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } finally {
            CloseUtils.close(oos);
            CloseUtils.close(fos);
        }
    }

    /**
     * 从文件里面读取对象
     *
     * @param filePath
     * @return
     */
    public static Object readObjectFromFile(String filePath) {
        return readObjectFromFile(new File(filePath));
    }

    public static Object readObjectFromFile(File file) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        } finally {
            CloseUtils.close(ois);
            CloseUtils.close(fis);
        }
        return null;
    }

    /**
     * 获取真实文件路径
     *
     * @param filePath 以/file/开头的虚拟文件路径
     * @return the real path of file
     */
    public static String getRealFilePath(String filePath) {
        return ConfigLoader.getUploadFilePath() + filePath.substring(6);
    }
}
