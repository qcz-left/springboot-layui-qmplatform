package com.qcz.qmplatform.common.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
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
import java.util.Date;

/**
 * 文件工具类
 */
public class FileUtils extends FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    public static final String WEB_PATH = getWebPath();

    /**
     * 虚拟文件前缀
     */
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
    public static void writeObjectToFile(Object obj, File file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        createIfNotExists(file);
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            IoUtil.close(oos);
            IoUtil.close(fos);
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

    public static <T> T readObjectFromFile(File file, Class<T> clazz) {
        if (!exist(file)) {
            return ReflectUtil.newInstance(clazz);
        }
        return readObjectFromFile(FileUtil.getInputStream(file), clazz);
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
        if (!exist(file)) {
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

    public static void createNewFile(File file) {
        boolean newFile = false;
        try {
            newFile = file.createNewFile();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        if (!newFile) {
            LOGGER.warn("create new file failed!");
        }
    }

    /**
     * 如果文件不存在就创建，如果父目录不存在也一并创建
     */
    public static void createIfNotExists(File file) {
        if (!exist(file)) {
            File parentFile = file.getParentFile();
            if (!exist(parentFile)) {
                mkdir(parentFile);
            }
            createNewFile(file);
        }
    }

    public static void createIfNotExists(String filePath) {
        createIfNotExists(file(filePath));
    }

    /**
     * 如果文件夹不存在就创建
     */
    public static void createDirIfNotExists(File file) {
        if (!exist(file)) {
            mkdir(file);
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
        if (isVirtualFile(filePath)) {
            return ConfigLoader.getUploadFilePath() + filePath.substring(6);
        }
        return filePath;
    }

    /**
     * 是否虚拟文件
     */
    public static boolean isVirtualFile(String filePath) {
        return StringUtils.startWith(filePath, PATH_PRE);
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

    /**
     * 根据原始文件名生成临时文件路径
     *
     * @param fileName 文件名
     */
    public static String generateTmpFilePath(String fileName) {
        return ConfigLoader.getDeleteTmpPath() + DateUtils.format(new Date(), DatePattern.PURE_DATETIME_MS_FORMAT) + "_" + fileName;
    }

    /**
     * 通过虚拟文件路径删除文件
     *
     * @param virtualFilePath 以 PATH_PRE 开头的虚拟文件路径
     */
    public static void delByVirtualPath(String virtualFilePath) {
        FileUtils.del(getRealFilePath(virtualFilePath));
    }

}
