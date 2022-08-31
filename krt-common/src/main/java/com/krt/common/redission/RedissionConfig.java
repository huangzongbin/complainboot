package com.krt.common.redission;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Slf4j
@Configuration
public class RedissionConfig {

    @Resource
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient(){
        RedissonClient redissonClient;
        Config config = new Config();
        Integer port = redisProperties.getPort();
        if (port == 0) {
            port = 6379;
        }
        String url = "redis://" + redisProperties.getHost()
                + ":" + port;
        config.useSingleServer().setAddress(url) //单机
                .setPassword(redisProperties.getPassword())
                .setDatabase(redisProperties.getDatabase());
        //添加主从配置
        //config.useMasterSlaveServers().setMasterAddress("").setPassword("").addSlaveAddress(new String[]{"",""});
        //集群
        //config.useClusterServers().addNodeAddress(new String[]{"",""}).setPassword("");
        try {
            redissonClient = Redisson.create(config);
            return redissonClient;
        } catch (Exception e) {
            log.error("RedissonClient init redis url :[{}]",url,e);
            return null;
        }
    }

}