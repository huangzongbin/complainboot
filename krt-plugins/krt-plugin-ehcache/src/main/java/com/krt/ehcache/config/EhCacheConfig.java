package com.krt.ehcache.config;

import com.krt.common.cache.KrtCacheKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.Nullable;

/**
 * EhCache系统缓存key
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年04月10日
 */
@Slf4j
@EnableCaching
@Configuration
@ConditionalOnProperty(name = "web.cache",havingValue = "ehcache")
public class EhCacheConfig extends CachingConfigurerSupport {

    /**
     * 自定义key生成器
     */
    @Nullable
    @Override
    public KeyGenerator keyGenerator() {
        return new KrtCacheKeyGenerator();
    }

    /**
     * 据shared与否的设置,
     * Spring分别通过CacheManager.create()
     * 或new CacheManager()方式来创建一个ehcache基地.
     * <p>
     * 也说是说通过这个来设置cache的基地是这里的Spring独用,还是跟别的(如shiro的Ehcache共享)
     */
    @Bean("ehCacheManagerFactoryBean")
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }


    /**
     * ehcache 主要的管理器
     *
     * @return EhCacheCacheManager
     */
    @Bean("ehCacheCacheManager")
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean ehCacheManagerFactoryBean) {
        log.debug("--------------ehCacheManager spring init---------------");
        CacheManager cacheManager = ehCacheManagerFactoryBean.getObject();
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager(cacheManager);
        log.debug("--------------ehCacheManager spring end---------------");
        return ehCacheCacheManager;
    }

    /**
     * 获取shiro的ehCache缓存实现
     */
    @Bean("shiroCacheManager")
    public EhCacheManager ehCacheManager(EhCacheCacheManager ehCacheCacheManager) {
        log.debug("--------------ehCacheManager shiro init---------------");
        //使用spring的共享缓存
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(ehCacheCacheManager.getCacheManager());
        log.debug("--------------ehCacheManager shiro end---------------");
        return ehCacheManager;
    }
}
