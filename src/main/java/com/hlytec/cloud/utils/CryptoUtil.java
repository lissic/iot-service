package com.hlytec.cloud.utils;


import java.nio.charset.StandardCharsets;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;


/**
 * @description: CryptoUtil 加解密工具类
 * @author: zero
 * @date: 2021/4/16 15:59
 */
public final class CryptoUtil {

    private static final String KEY = "hlyt netservices";

    private CryptoUtil() {
    }

    /**
     * 加密密钥
     * @return byte[]
     */
    private static byte[] key() {
        return KEY.getBytes(StandardCharsets.UTF_8);
    }
    /**
     * 加密密码
     * @param password 明文密码
     * @return 十六进制密文
     */
    public static String encryptPwd(String password){
        //构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key());
        //加密为16进制表示
        return aes.encryptHex(password);
    }

    /**
     * 解密密码
     * @param encryptHex 十六进制密文
     * @return 明文
     */
    public static String decryptPwd(String encryptHex){
        //构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key());
        //解密为字符串
        return aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
    }

}
