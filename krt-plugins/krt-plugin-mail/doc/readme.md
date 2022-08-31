#邮件插件使用说明

- 1、在启动器的配置文件中添加以下参数

        spring.mail.host=smtp.qiye.163.com //邮箱服务器地址
        spring.mail.username=xxx@oo.com //用户名
        spring.mail.password=xxyyooo    //密码
        spring.mail.default-encoding=UTF-8
        mail.fromMail.addr=xxx@oo.com  //以谁来发送邮件
        
- 2、需要使用注入
        
         @Autowired
         private IMailService mailService;