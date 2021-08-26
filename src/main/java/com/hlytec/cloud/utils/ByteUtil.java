package com.hlytec.cloud.utils;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;

/**
 * @description: ByteUtil 字节操作工具类
 * @author: zero
 * @date: 2021/4/16 17:00
 */
public class ByteUtil {

    private static final String CHARSET_NAME = "UTF-8";

    public static final byte[] EMPTY = new byte[0];

    /**
     * 字符串转字节数组
     *
     * @param str str
     * @return byte[]
     */
    public static byte[] toBytes(String str) {
        if (str == null) {
            return EMPTY;
        }
        return str.getBytes(Charset.forName(CHARSET_NAME));
    }

    /**
     * 对象转字节数组
     *
     * @param str str
     * @return byte[]
     */
    public static byte[] toBytes(Object str) {
        if (str == null) {
            return EMPTY;
        }
        return toBytes(String.valueOf(str));
    }

    /**
     * 字节数组转字符串
     *
     * @param bytes bytes
     * @return String
     */
    public static String toString(byte[] bytes) {
        if (bytes == null) {
            return StringUtils.EMPTY;
        }
        return new String(bytes, Charset.forName(CHARSET_NAME));
    }

    /**
     * 字节数组是否为空
     *
     * @param data data
     * @return boolean
     */
    public static boolean isEmpty(byte[] data) {
        return data == null || data.length == 0;
    }

    /**
     * 字节数组是否不为空
     *
     * @param data data
     * @return boolean
     */
    public static boolean isNotEmpty(byte[] data) {
        return !isEmpty(data);
    }
}
