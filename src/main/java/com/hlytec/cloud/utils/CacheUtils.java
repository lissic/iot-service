package com.hlytec.cloud.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.ehcache.Element;

import java.util.Iterator;
import java.util.Map;

/**
 * @description: CacheUtils
 * @author: JackChen
 * @date: 2021/5/18 17:58
 */
public class CacheUtils {

    private static Logger logger = LoggerFactory.getLogger(CacheUtils.class);
    private static CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);

    private static final String SYS_CACHE = "sysCache";

    /**
     * 获取SYS_CACHE缓存
     *
     * @param key
     * @return
     */
    public static Object get(String key) {
        return get(SYS_CACHE, key);
    }

    /**
     * 获取SYS_CACHE缓存
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object get(String key, Object defaultValue) {
        Object value = get(key);
        return value != null ? value : defaultValue;
    }

    /**
     * 从SYS_CACHE缓存中移除
     *
     * @param key
     * @return
     */
    public static void remove(String key) {
        remove(SYS_CACHE, key);
    }

    /**
     * 获取缓存
     *
     * @param cashName
     * @param KeyName
     * @return
     */
    public static Object getObjectForCash(String cashName, String KeyName) {
        Cache cache = cacheManager.getCache(cashName);
        Element element = cache.get(KeyName);
        return element == null ? null : element.getObjectValue();
    }

    /**
     * 写入缓存
     *
     * @param CashName
     * @param map
     */
    public static void putObjectForCash(String CashName, Map map) {
        Cache cache = cacheManager.getCache(CashName);
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry value = (Map.Entry) iterator.next();
            Element element = new Element(value.getKey(), value.getValue());
            cache.put(element);
        }
    }

    /**
     * 从缓存中移除
     *
     * @param cacheName
     * @param key
     */
    public static void remove(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        cache.remove(key);
    }

    /**
     * 从缓存中移除所有
     *
     * @param cacheName
     */
    public static void removeAll(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        cache.removeAll();
    }

    /**
     * 获得一个Cache
     *
     * @param cacheName
     * @param keyName
     * @return
     */
    private static Object getCache(String cacheName, String keyName) {
        Cache cache = cacheManager.getCache(cacheName);
        Element element = cache.get(keyName);
        return element == null ? null : element.getObjectValue();
    }

}
