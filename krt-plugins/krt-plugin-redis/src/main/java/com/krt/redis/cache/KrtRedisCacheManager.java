package com.krt.redis.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis实现shiro的缓存管理器
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月12日
 */
public class KrtRedisCacheManager implements CacheManager {

    private RedisTemplate redisTemplate;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new KrtRedisCache<>(name, redisTemplate);
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
