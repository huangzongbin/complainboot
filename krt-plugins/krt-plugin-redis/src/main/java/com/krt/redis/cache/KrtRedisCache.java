package com.krt.redis.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis实现shiro的cache接口
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月12日
 */
@Slf4j
public class KrtRedisCache<K, V> implements Cache<K, V> {

    private RedisTemplate<K, V> redisTemplate;

    /**
     * 缓存前缀
     */
    private static final String REDIS_SHIRO_CACHE = "krt-shiro-cache:";

    /**
     * 缓存时间
     */
    private long globExpire = 30;

    /**
     * 缓存前缀
     */
    private String keyPrefix;

    public KrtRedisCache(String name, RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.keyPrefix = REDIS_SHIRO_CACHE + name + ":";
    }


    @Override
    public V get(K key) throws CacheException {
        //判断key是不是包含了前缀
        if (key.toString().startsWith(keyPrefix)) {
            redisTemplate.boundValueOps(key).expire(globExpire, TimeUnit.MINUTES);
            return redisTemplate.boundValueOps(key).get();
        } else {
            redisTemplate.boundValueOps(getCacheKey(key)).expire(globExpire, TimeUnit.MINUTES);
            return redisTemplate.boundValueOps(getCacheKey(key)).get();
        }
    }

    @Override
    public V put(K key, V value) throws CacheException {
        V old = get(key);
        redisTemplate.boundValueOps(getCacheKey(key)).set(value);
        redisTemplate.boundValueOps(getCacheKey(key)).expire(globExpire, TimeUnit.MINUTES);
        return old;
    }

    @Override
    public V remove(K key) throws CacheException {
        V old = get(key);
        redisTemplate.delete(getCacheKey(key));
        return old;
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete(keys());
    }

    @Override
    public int size() {
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        return redisTemplate.keys(getCacheKey("*"));
    }

    @Override
    public Collection<V> values() {
        Set<K> set = keys();
        List<V> list = new ArrayList<>();
        for (K s : set) {
            list.add(get(s));
        }
        return list;
    }

    private K getCacheKey(Object k) {
        return (K) (this.keyPrefix + k);
    }
}
