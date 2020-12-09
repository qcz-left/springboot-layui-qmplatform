package com.qcz.qmplatform.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class FileUtils extends org.apache.commons.io.FileUtils {

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
}
