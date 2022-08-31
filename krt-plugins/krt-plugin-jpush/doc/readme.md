#极光推送插件使用说明

- 1、在启动器的配置文件中添加以下参数

        krt.jpush.secret = 22
        krt.jpush.appkey = 11
        
- 2、需要使用注入
        
         @Autowired
         private IPushService pushService;