package com.krt.ehcache.controller;

import com.google.common.collect.Lists;
import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.ReturnBean;
import com.krt.common.util.DateUtils;
import com.krt.common.util.PrettyMemoryUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.statistics.StatisticsGateway;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * ehcache 缓存管理控制层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年05月24日
 */
@Slf4j
@Controller
@ConditionalOnProperty(name = "web.cache", havingValue = "ehcache")
public class EhcacheController extends BaseController {

    @Autowired
    private EhCacheCacheManager ehCacheCacheManager;

    /**
     * 缓存管理
     */
    @RequiresPermissions("ehcache:list")
    @GetMapping("ehcache/list")
    public String list() {
        CacheManager cacheManager = ehCacheCacheManager.getCacheManager();
        List<Map<String, Object>> list = new ArrayList<>();
        assert cacheManager != null;
        String[] cacheNames = cacheManager.getCacheNames();
        for (String cacheName : cacheNames) {
            Cache cache = cacheManager.getCache(cacheName);
            //缓存统计
            Map<String, Object> map = new HashMap<>(9);
            StatisticsGateway statistics = cache.getStatistics();
            map.put("cacheName", cacheName);
            map.put("cacheHitCount", statistics.cacheHitCount());
            map.put("cacheMissCount", statistics.cacheMissCount());
            long totalCount = statistics.cacheHitCount() + statistics.cacheMissCount();
            totalCount = totalCount > 0 ? totalCount : 1;
            map.put("totalCount", totalCount);
            map.put("cacheHitPercent", String.format("%.2f", (double) (statistics.cacheHitCount() * 100 / totalCount)));
            map.put("localHeapCount", statistics.getLocalHeapSize());
            map.put("localDiskCount", statistics.getLocalDiskSize());
            map.put("localHeapSize", PrettyMemoryUtils.prettyByteSize(statistics.getLocalHeapSize()));
            map.put("localDiskSize", PrettyMemoryUtils.prettyByteSize(statistics.getLocalDiskSize()));
            list.add(map);
        }
        request.setAttribute("list", list);
        return "ehcache/list";
    }

    /**
     * 缓存详情
     *
     * @param cacheName 缓存名称
     */
    @RequiresPermissions("ehcache:cacheNameDetail")
    @GetMapping("ehcache/cacheNameDetail")
    public String cacheNameDetail(String cacheName) {
        CacheManager cacheManager = ehCacheCacheManager.getCacheManager();
        assert cacheManager != null;
        List nonExpiryKeys = cacheManager.getCache(cacheName).getKeys();
        List<Object> caches = Lists.newArrayList();
        for (Object key : nonExpiryKeys) {
            Element element = cacheManager.getCache(cacheName).get(key);
            Map<String, Object> map = new HashMap<>(10);
            map.put("key", key);
            map.put("objectValue", element.getObjectValue().toString());
            map.put("size", PrettyMemoryUtils.prettyByteSize(element.getSerializedSize()));
            map.put("hitCount", element.getHitCount());
            Date latestOfCreationAndUpdateTime = new Date(element.getLatestOfCreationAndUpdateTime());
            map.put("latestOfCreationAndUpdateTime", DateUtils.dateToString("yyyy-MM-dd HH:mm:ss", latestOfCreationAndUpdateTime));
            Date lastAccessTime = new Date(element.getLastAccessTime());
            map.put("lastAccessTime", DateUtils.dateToString("yyyy-MM-dd HH:mm:ss", lastAccessTime));
            if (element.getExpirationTime() == Long.MAX_VALUE) {
                map.put("expirationTime", "不过期");
            } else {
                Date expirationTime = new Date(element.getExpirationTime());
                map.put("expirationTime", DateUtils.dateToString("yyyy-MM-dd HH:mm:ss", expirationTime));
            }
            map.put("timeToIdle", element.getTimeToIdle());
            map.put("timeToLive", element.getTimeToLive());
            map.put("version", element.getVersion());
            caches.add(map);
        }
        request.setAttribute("caches", caches);
        request.setAttribute("cacheName", cacheName);
        return "ehcache/cacheNameDetail";
    }

    /**
     * 删除缓存
     *
     * @param cacheName 缓存名称
     * @param key       缓存key
     */
    @KrtLog("删除缓存")
    @RequiresPermissions("ehcache:delete")
    @PostMapping("ehcache/delete")
    @ResponseBody
    public ReturnBean delete(String cacheName, String key) {
        CacheManager cacheManager = ehCacheCacheManager.getCacheManager();
        assert cacheManager != null;
        Cache cache = cacheManager.getCache(cacheName);
        //删除单个缓存
        if (key != null && !"".equals(key)) {
            cache.remove(key);
        } else {
            //删除单类缓存
            cache.removeAll();
        }
        return ReturnBean.ok();
    }

    /**
     * 清空系统缓存
     */
    @KrtLog("清空系统缓存")
    @RequiresRoles(value = "admin")
    @PostMapping("cache/deleteAll")
    @ResponseBody
    public ReturnBean deleteAll() {
        CacheManager cacheManager = ehCacheCacheManager.getCacheManager();
        assert cacheManager != null;
        cacheManager.clearAll();
        return ReturnBean.ok();
    }


}
