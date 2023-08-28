package com.qcz.qmplatform.common.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 文件工具类
 */
public class FileUtils extends FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    public static final String WEB_PATH = getWebPath();

    public static final String PATH_PRE = "/file/";

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
            LOGGER.error(e.getMessage(), e);
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
            return ReflectUtil.newInstance(clazz);
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            CloseUtils.close(ois);
            CloseUtils.close(fis);
        }
        return ReflectUtil.newInstance(clazz);
    }

    public static <T> T readObjectFromFile(InputStream inputStream, Class<T> clazz) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(inputStream);
            return clazz.cast(ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            CloseUtils.close(ois);
        }
        return ReflectUtil.newInstance(clazz);
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
            LOGGER.error(e.getMessage(), e);
        } finally {
            CloseUtils.close(ois);
            CloseUtils.close(fis);
        }
        return new Object();
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

    public static void createIfNotExists(String filePath) {
        createIfNotExists(file(filePath));
    }

    /**
     * 如果文件夹不存在就创建
     */
    public static void createDirIfNotExists(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void createDirIfNotExists(String dirPath) {
        createDirIfNotExists(file(dirPath));
    }

    /**
     * 获取真实文件路径
     *
     * @param filePath 以/file/开头的虚拟文件路径
     * @return the real path of file
     */
    public static String getRealFilePath(String filePath) {
        if (StringUtils.startWith(filePath, PATH_PRE)) {
            return ConfigLoader.getUploadFilePath() + filePath.substring(6);
        }
        return filePath;
    }

    /**
     * 获取文件名后缀
     */
    public static String getFileSuf(String filePathOrName) {
        return filePathOrName.substring(filePathOrName.lastIndexOf(".") + 1);
    }

    private static String getWebPath() {
        try {
            String webPath = new ClassPathResource("").getFile().getCanonicalPath();
            // 本地运行
            if (webPath.contains("target\\classes")) {
                return new File("../").getCanonicalPath();
            }
            // war 运行
            String packing = YmlPropertiesUtils.getPacking();
            if ("war".equals(packing)) {
                return new File("../../").getCanonicalPath();
            }
            // 以jar包部署方式路径则为jar所在目录
            return webPath;
        } catch (IOException e) {
            LOGGER.error(null, e);
        }
        return "/";
    }

}
