package com.krt.redis.cache;

import org.springframework.data.redis.cache.CacheKeyPrefix;

/**
 * 自定义缓存分隔 默认spring cache 是"::"  改成 ":"
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年09月14日
 */
@FunctionalInterface
public interface KrtRedisCacheKeyPrefix extends CacheKeyPrefix {

    /**
     * 比较方法
     *
     * @param s 字符串
     * @return {@link String}
     */
    @Override
    String compute(String s);

    /**
     * redis 缓存key 分隔符
     *
     * @return 缓存前缀
     */
    static CacheKeyPrefix simple() {
        return (name) -> {
            return name + ":";
        };
    }
}
