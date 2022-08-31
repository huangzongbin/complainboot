package com.krt.framework.config;

import com.krt.framework.shiro.KrtWebSessionManager;
import com.krt.framework.shiro.RetryLimitHashedCredentialsMatcher;
import com.krt.framework.shiro.ShiroRealm;
import com.krt.redis.dao.RedisSessionDAO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 初始化shiro
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年7月18日
 */
@Slf4j
@Configuration
public class ShiroConfig {

    /**
     * sessionDao 默认使用内存保存session
     */
    @Bean
    @ConditionalOnProperty(name = "web.session", havingValue = "memory")
    public SessionDAO localSessionDao() {
        return new MemorySessionDAO();
    }

    /**
     * sessionDao 使用redis缓存session
     */
    @Bean
    @ConditionalOnProperty(name = "web.session", havingValue = "redis")
    public SessionDAO redisSessionDAO(CacheManager cacheManager) {
        RedisSessionDAO dao = new RedisSessionDAO();
        dao.setCacheManager(cacheManager);
        dao.setActiveSessionsCacheName("redisSession");
        return dao;
    }

    /**
     * cookie对象
     *
     * @return {@link SimpleCookie}
     */
    @Bean
    public SimpleCookie simpleCookie() {
        SimpleCookie cookie = new SimpleCookie();
        cookie.setName("krt-session-id");
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

    /**
     * session管理器
     */
    @Bean
    public KrtWebSessionManager sessionManager(CacheManager cacheManager, SessionDAO sessionDAO, SimpleCookie simpleCookie) {
        KrtWebSessionManager sessionManager = new KrtWebSessionManager();
        // 加入缓存管理器
        sessionManager.setCacheManager(cacheManager);
        // 删除过期的session
        sessionManager.setDeleteInvalidSessions(true);
        // 设置全局session超时时间 springboot的session失效
        sessionManager.setGlobalSessionTimeout(30 * 60 * 1000);
        // 是否定时检查session
        sessionManager.setSessionValidationSchedulerEnabled(true);
        // 定时检查无效session 10分钟检查一次
        sessionManager.setSessionValidationInterval(10 * 60 * 1000);
        //使用RedisSessionDAO 则session有效期在KrtRedisCache设置
        sessionManager.setSessionDAO(sessionDAO);
        //设置cookie
        sessionManager.setSessionIdCookie(simpleCookie);
        return sessionManager;
    }

    /**
     * 密码匹配凭证管理器
     *
     * @return {@link HashedCredentialsMatcher}
     */
    @Bean
    public RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher(cacheManager);
        // 采用MD5方式加密
        retryLimitHashedCredentialsMatcher.setHashAlgorithmName("MD5");
        // 设置加密次数
        retryLimitHashedCredentialsMatcher.setHashIterations(1024);
        return retryLimitHashedCredentialsMatcher;
    }

    /**
     * 配置自定义的权限登录器
     *
     * @param retryLimitHashedCredentialsMatcher 密码匹配凭证
     * @return {@link ShiroRealm}
     */
    @Bean
    public ShiroRealm shiroRealm(RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher) {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setAuthorizationCacheName("authorizationCache");
        shiroRealm.setCredentialsMatcher(retryLimitHashedCredentialsMatcher);
        return shiroRealm;
    }

    /**
     * 权限管理
     *
     * @param cacheManager   缓存管理器
     * @param shiroRealm     自定义Realm
     * @param sessionManager session管理器
     * @return {@link DefaultWebSecurityManager}
     */
    @Bean
    public DefaultWebSecurityManager securityManager(CacheManager cacheManager, ShiroRealm shiroRealm, KrtWebSessionManager sessionManager) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 设置realm.
        manager.setRealm(shiroRealm);
        // 注入缓存管理器
        manager.setCacheManager(cacheManager);
        // session管理器
        manager.setSessionManager(sessionManager);
        return manager;
    }

    /**
     * 过滤规则
     *
     * @param securityManager 权限管理器
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        // Shiro的核心安全接口,这个属性是必须的
        shiroFilter.setSecurityManager(securityManager);
        //登录页
        shiroFilter.setLoginUrl("/login");
        //无权限页
        shiroFilter.setUnauthorizedUrl("/error/403");
        //规则设置
        Map<String, String> filterMap = new LinkedHashMap<>();
        //静态文件
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/dist/**", "anon");
        filterMap.put("/plugin/**", "anon");
        filterMap.put("/upload/**", "anon");
        filterMap.put("/captcha.*", "anon");
        filterMap.put("/html/**", "anon");
        //swagger配置
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/swagger**", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/configuration/ui", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        //开放访问
        filterMap.put("/sliderImage", "anon");
        filterMap.put("/checkSliderImage", "anon");
        filterMap.put("/captcha", "anon");
        filterMap.put("/login", "anon");
        filterMap.put("/api/**", "anon");
        filterMap.put("/file/**", "anon");
        filterMap.put("/ws/**", "anon");
        filterMap.put("/oauth/*", "anon");
        //其他
        filterMap.put("/wechat/**", "anon");
        filterMap.put("/logout", "logout");
        filterMap.put("/**", "authc");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    /**
     * 保证实现了Shiro内部lifecycle函数的bean执行
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 支持Shiro的注释
     *
     * @param securityManager 管理器
     * @return {@link AuthorizationAttributeSourceAdvisor}
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}