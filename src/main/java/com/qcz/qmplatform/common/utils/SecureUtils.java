package com.qcz.qmplatform.common.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import com.qcz.qmplatform.common.constant.Constant;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 加解密工具
 */
public class SecureUtils {

    public static final String PASSWORD_UNCHANGED = "******";

    private static final AES AES = SecureUtil.aes(SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded());

    private static final DES DES = SecureUtil.des();

    /**
     * MD5加密
     *
     * @param saltName 盐
     * @param password 明文密码
     * @return 加密后的字符串
     */
    public static String simpleMD5(String saltName, String password) {
        Object salt = ByteSource.Util.bytes(saltName);

        Object result = new SimpleHash(Constant.SUBJECT_ALGORITHM_NAME_MD5, password, salt, Constant.SUBJECT_HASHTERATIONS);
        return result.toString();
    }

    /**
     * AES加密
     *
     * @param str 待加密的字符串
     * @return 加密后的字符串
     */
    public static String aesEncrypt(String str) {
        return AES.encryptBase64(str);
    }

    /**
     * AES解密
     *
     * @param str 待解密的字符串
     * @return 解密后的字符串
     */
    public static String aesDecrypt(String str) {
        return AES.decryptStr(str);
    }

    /**
     * DES加密
     *
     * @param str 待加密的字符串
     * @return 加密后的字符串
     */
    public static String desEncrypt(String str) {
        return DES.encryptBase64(str);
    }

    /**
     * DES解密
     *
     * @param str 待解密的字符串
     * @return 解密后的字符串
     */
    public static String desDecrypt(String str) {
        return DES.decryptStr(str);
    }

    /**
     * 密码是否发生变化
     *
     * @param pwd the password
     */
    public static boolean passwordChanged(String pwd) {
        return !StringUtils.equals(PASSWORD_UNCHANGED, pwd);
    }
}
