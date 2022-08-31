package com.krt.redis.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.krt.common.cache.KrtCacheKeyGenerator;
import com.krt.redis.cache.KrtRedisCacheKeyPrefix;
import com.krt.redis.cache.KrtRedisCacheManager;
import com.krt.redis.cache.KrtRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * redis缓存初始化
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月11日
 */
@Slf4j
@Configuration
@EnableCaching
@ConditionalOnProperty(name = "web.cache", havingValue = "redis")
public class RedisCacheConfig extends CachingConfigurerSupport {

    @Value("${web.token-expire}")
    private Long tokenExpire;

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;

    /**
     * 自定义key生成器
     */
    @Nullable
    @Override
    public KeyGenerator keyGenerator() {
        return new KrtCacheKeyGenerator();
    }

    /**
     * 初始化springcache 的redis实现
     */
    @Override
    @Bean("redisCacheManager")
    public CacheManager cacheManager() {
        log.debug("--------------redis spring cache init---------------");
        //设置value序列化
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(new GenericFastJsonRedisSerializer());
        //自定义accessTokenCache配置
        RedisCacheConfiguration accessTokenCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                //设置序列化
                .serializeValuesWith(pair)
                //设置默认超过期时间
                .entryTtl(Duration.ofSeconds(tokenExpire))
                ////设置分隔符
                .computePrefixWith(KrtRedisCacheKeyPrefix.simple());
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>(1);
        redisCacheConfigurationMap.put("accessTokenCache", accessTokenCacheConfiguration);
        //自定义passwordRetryCache配置
        RedisCacheConfiguration passwordRetryCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                //设置序列化
                .serializeValuesWith(pair)
                //设置默认超过期时间
                .entryTtl(Duration.ofSeconds(600))
                ////设置分隔符
                .computePrefixWith(KrtRedisCacheKeyPrefix.simple());
        redisCacheConfigurationMap.put("passwordRetryCache", passwordRetryCacheConfiguration);
        //设置默认缓存配置
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                //设置序列化
                .serializeValuesWith(pair)
                //设置默认超过期时间是1天
                .entryTtl(Duration.ofDays(1))
                //设置分隔符
                .computePrefixWith(KrtRedisCacheKeyPrefix.simple());
        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(lettuceConnectionFactory);
        //初始化RedisCacheManager
        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig, redisCacheConfigurationMap);
        log.debug("--------------redis spring cache end---------------");
        return cacheManager;
    }

    /**
     * 初始化shirocache 的redis实现
     */
    @Bean("shiroCacheManager")
    public KrtRedisCacheManager krtRedisCacheManager(KrtRedisTemplate krtRedisTemplate) {
        log.debug("--------------redis shiro cache init---------------");
        KrtRedisCacheManager krtRedisCacheManager = new KrtRedisCacheManager();
        krtRedisCacheManager.setRedisTemplate(krtRedisTemplate);
        log.debug("--------------redis shiro cache end ---------------");
        return krtRedisCacheManager;
    }


}
