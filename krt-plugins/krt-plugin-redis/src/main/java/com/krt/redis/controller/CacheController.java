package com.krt.redis.controller;

import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.ReturnBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ehcache 缓存管理控制层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年05月24日
 */
@Slf4j
@Controller
@ConditionalOnProperty(name = "web.cache", havingValue = "redis")
public class CacheController extends BaseController {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 清空系统缓存
     */
    @KrtLog("清空系统缓存")
    @RequiresRoles(value = "admin")
    @PostMapping("cache/deleteAll")
    @ResponseBody
    public ReturnBean deleteAll() {
        redisTemplate.execute((RedisCallback) connection -> {
            connection.flushDb();
            log.debug("清控系统缓存成功");
            return "ok";
        });
        return ReturnBean.ok();
    }


}
