#fastdfs
    
    # fastdfs 配置
    fdfs:
        # 超时毫秒
        connect-timeout: 30000
        # 连接池
        pool:
            max-total: 100
            max-wait-millis: 30000
        so-timeout: 30000
        # 缩略图
        thumb-image:
            height: 150
            width: 150
        # 跟踪器（多个逗号隔开）
        tracker-list: 172.0.52.41:22122,172.0.52.42:22122