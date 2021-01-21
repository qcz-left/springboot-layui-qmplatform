package com.qcz.qmplatform.common.utils;

import cn.hutool.core.io.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileUtils extends FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    public static final String WEB_PATH = getWebPath();

    /**
     * 将对象写到文件
     *
     * @param obj      对象
     * @param filePath 文件路径
     */
    public static void writeObjectToFile(Object obj, String filePath) {
        writeObjectToFile(obj, new File(filePath));
    }

    /**
     * 将对象写到文件
     *
     * @param obj  对象
     * @param file 文件路径
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void writeObjectToFile(Object obj, File file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
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
     * @param filePath 文件路径
     * @return 对象
     */
    public static <T> T readObjectFromFile(String filePath, Class<T> clazz) {
        return readObjectFromFile(new File(filePath), clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T readObjectFromFile(File file, Class<T> clazz) {
        if (!file.exists()) {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                LOGGER.error(null, e);
            }
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        } finally {
            CloseUtils.close(ois);
            CloseUtils.close(fis);
        }
        return null;
    }

    public static Object readObjectFromFile(String filePath) {
        return readObjectFromFile(new File(filePath));
    }

    public static Object readObjectFromFile(File file) {
        if (!file.exists()) {
            try {
                return new Object();
            } catch (Exception e) {
                LOGGER.error(null, e);
            }
        }
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
     * 如果文件不存在就创建，如果父目录不存在也一并创建
     */
    public static void createIfNotExists(File file) {
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                LOGGER.error(null, e);
            }
        }
    }

    /**
     * 获取真实文件路径
     *
     * @param filePath 以/file/开头的虚拟文件路径
     * @return the real path of file
     */
    public static String getRealFilePath(String filePath) {
        if (StringUtils.containsAny(filePath, "/file/")) {
            return ConfigLoader.getUploadFilePath() + filePath.substring(6);
        }
        return filePath;
    }

    private static String getWebPath() {
        try {
            String webPath = new ClassPathResource("").getFile().getCanonicalPath();
            if (webPath.contains("target\\classes")) {
                return new File("../").getCanonicalPath();
            }
            return webPath;
        } catch (IOException e) {
            LOGGER.error(null, e);
        }
        return "/";
    }

}
