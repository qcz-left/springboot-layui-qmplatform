package com.qcz.qmplatform.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 加解密工具
 */
public class SecureUtils {

    public static final String PASSWORD_UNCHANGED = "******";

    public static final String DEFAULT_PASSWORD = "123456";

    private static final AES AES_INSTANCE = SecureUtil.aes(ConfigLoader.getAesKey().getBytes());

    private static final DES DES_INSTANCE = SecureUtil.des(ConfigLoader.getDesKey().getBytes());

    private static final RSA RSA_INSTANCE = SecureUtil.rsa(ConfigLoader.getRsaPrivateKey(), ConfigLoader.getRsaPublicKey());

    /**
     * 账号加密
     *
     * @param password 明文密码
     * @return 加密后的字符串
     */
    public static String accountEncrypt(String password) {
        return bcrypt(password);
    }

    /**
     * 检查密码是否一致
     *
     * @param plaintext 明文密码
     * @param hashed    数据库中加密的密码
     * @return 加密后的字符串
     */
    public static boolean accountCheck(String plaintext, String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }

    /**
     * BC加密
     *
     * @param password 明文密码
     * @return 加密后的字符串
     */
    public static String bcrypt(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    /**
     * AES加密
     *
     * @param str 待加密的字符串
     * @return 加密后的字符串
     */
    public static String aesEncrypt(String str) {
        return AES_INSTANCE.encryptBase64(str);
    }

    /**
     * AES解密
     *
     * @param str 待解密的字符串
     * @return 解密后的字符串
     */
    public static String aesDecrypt(String str) {
        if (StringUtils.isBlank(str)) {
            return StringUtils.EMPTY;
        }
        return AES_INSTANCE.decryptStr(str);
    }

    /**
     * DES加密
     *
     * @param str 待加密的字符串
     * @return 加密后的字符串
     */
    public static String desEncrypt(String str) {
        return DES_INSTANCE.encryptBase64(str);
    }

    /**
     * DES解密
     *
     * @param str 待解密的字符串
     * @return 解密后的字符串
     */
    public static String desDecrypt(String str) {
        return DES_INSTANCE.decryptStr(str);
    }

    /**
     * RSA加密
     *
     * @param str 待加密的字符串
     * @return 加密后的字符串
     */
    public static String rsaEncrypt(String str) {
        return RSA_INSTANCE.encryptBase64(str, KeyType.PublicKey);
    }

    /**
     * RSA解密
     *
     * @param str 待解密的字符串
     * @return 解密后的字符串
     */
    public static String rsaDecrypt(String str) {
        return RSA_INSTANCE.decryptStr(str, KeyType.PrivateKey);
    }

    /**
     * 扩展使用其它私钥的RSA解密
     *
     * @param str 待解密的字符串
     * @return 解密后的字符串
     */
    public static String rsaDecrypt(String privateKey, String str) {
        return SecureUtil.rsa(privateKey, null).decryptStr(str, KeyType.PrivateKey);
    }

    /**
     * 密码是否发生变化
     *
     * @param pwd the password
     */
    public static boolean passwordChanged(String pwd) {
        return !StringUtils.equals(PASSWORD_UNCHANGED, pwd);
    }

    public static byte[] hmac256(byte[] key, String msg) {
        HMac hMac = new HMac(HmacAlgorithm.HmacSHA256, key);
        return hMac.digest(msg);
    }

    public static String sha256Hex(String msg) {
        Digester sha256 = new Digester(DigestAlgorithm.SHA256);
        return sha256.digestHex(msg);
    }

    public static void main(String[] args) {
        /*List<File> nacArcFiles = FileUtils.loopFiles(new File("D:\\workspace\\code\\main\\Agentless_Util\\uad"));
        List<File> uniArcFiles = FileUtils.loopFiles(new File("D:\\workspace\\code\\version5protocol"));
        Set<String> nacArcFileNames = new HashSet<>();
        Set<String> uniArcFileNames = new HashSet<>();
        for (File nacArcFile : nacArcFiles) {
            String name = nacArcFile.getName();
            if ("uad".equals(FileUtils.getFileSuf(name))) {
                nacArcFileNames.add(name);
            }
        }
        for (File uniArcFile : uniArcFiles) {
            String name = uniArcFile.getName();
            if ("uad".equals(FileUtils.getFileSuf(name))) {
                uniArcFileNames.add(uniArcFile.getName());
            }
        }

        Collection<String> intersection = CollectionUtil.intersection(nacArcFileNames, uniArcFileNames);
        for (String s : intersection) {
            System.out.println(s);
        }*/

        ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd HHmm"));

    }
}
