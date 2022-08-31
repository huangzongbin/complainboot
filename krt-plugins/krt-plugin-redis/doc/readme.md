#redis推送插件使用说明

- 1、在启动器的配置文件中添加以下参数

        # Redis数据库索引（默认为0）
        spring.redis.database=0
        # Redis服务器地址
        spring.redis.host=127.0.0.1
        # Redis服务器连接端口
        spring.redis.port=6379
        # Redis服务器连接密码（默认为空）
        spring.redis.password= root123456
        # 连接超时时间（毫秒）
        spring.redis.timeout=3600
        # 连接池lettuce（spring2.0默认使用的不是jedis）
        #最大连接数
        spring.redis.lettuce.pool.max-active=50
        #连接超时
        spring.redis.lettuce.pool.max-wait=5000
        #最大闲置数
        spring.redis.lettuce.pool.max-idle=1
        #最小闲置数
        spring.redis.lettuce.pool.min-idle=1
         