#QQ记录
    springboot2.0 版本更新 
    svn: https://192.168.1.251:9008/svn/krt251/admin/v3.0/krt
    业务模块化拆分、功能做成插拔式插件 
    目前做成插件的有：
    极光推送
    文件系统fastdfs
    定时器
    验证码
    ehcache缓存
    redis缓存（与ehcache随意切换）
    websocket
    除核心模块common、sys、web 其他模块随意删除
    demo地址：http://192.168.1.186:8080/login
    
    
     sb2.0版本更新 
     1、新增邮件插件  
     2、新增数据权限注解@KrtDataScope  （目前系统是通过组织结构实现） 也可用此做法实现其他数据权限
     具体用法查看用户管理  实现技术mybatis拦截器 和 spring aop  
     3、数据库结构更新
     4、bug修改   
     
     sb2.0版本更新 
     1、框架样式优化，登录页修改、新增Pro皮肤配色
     2、修复context-path路径问题
     3、其他bug修改
     demo地址：http://192.168.1.186:8080/krt/login
     
     sb2.0版本更新 
     1、新增auth2.0 shiro集成
     2、新增插件消息队列activeMQ， 同时支持queue 和 topic 模式
     3、其他bug修改
     svn:https://192.168.1.251:9008/svn/krt251/admin/v3.0/tags
     
     sb2.0版本更新 
     1、优化代码生成器： bug修改 、 新增列表选择组件 单选 多选 （默认选择id和列表的第一个字段 可以自定义修改）
     svn:https://192.168.1.251:9008/svn/krt251/admin/v3.0/tags
     demo：http://192.168.1.186:8888/krt admin 123456
     
     sb2.0版本更新 
     1、优化代码生成器： bug修改 ， 优化列表字典关联显示
     
     #sb2.0版本1.0.3完善
     1、新增thumbnailator图片处理jar，具体用法可以看common 的ImageUtils例子
     2、调整mybatisPlus 更新策略
        之前：为null或空字符串 数据库不更新
        之后： 为null 的不更新 空字符串更新数据库
     3、优化xss过滤器 新增excludes 排除url提升系统性能和部分如含有富文本的请求不需要xss过滤url 在web==>config==>FilterConfig配置
     4、优化定时器模块 支持多个web系统共用定时器 
        之前:全部在系统管理的web系统引入定时器
        之后：有定时器的业务web系统 都可以引入
     
     #sb2.0版本1.0.3完善
     1、优化系统样式
     2、日志模块优化新增记录客户端设备、系统版本、浏览器类型
     3、优化区域、组织机构模块提升查询效率 新增前端查询字典缓存
     4、优化mybatisPlus 根据配置文件类型判断是否刷新Mapper
     5、bug修改  
      
     #sb2.0版本1.0.4
     1、新增krt-monitor 项目整合spring boot admin 
         可监控多个springboot项目 
         服务宕机　上线　邮件通知 
         权限控制
         功能强大 更适合有多个子项目监控  单个的可用JavaMelody监控
     2、整合flyway用于数据库版本控制
     3、添加linux环境运行脚本 springboot.sh
     4、bug修改
 
     svn： https://192.168.1.251:9008/svn/krt251/admin/v3.0/tags/krt_1.0.4
     后台：http://192.168.1.186:8888/krt/  admin  123456
     spring boot admin :http://192.168.1.186:8889/krt-monitor/   krt  123456
     
      #sb2.0版本1.0.4
      优化redis 插件 支持多数据源 指定dbindex
      
     sb2.0 框架升级 1.0.5
     1、升级平台上传插件 (黄以龙配合完成、可以查看系统demo模块) （demo不能上传因为测试服务器不能连接云计算网络）
     	合并kindeditor 和 upload 插件 到 upload （不支持文件管理）
             剔除fastdfs 插件  新增模块 krt-file 集成fastdfs 和文件管理模块
             本地上传 和  fastdfs 无缝切换 只要添加不同依赖即可
     	本地依赖：
     	 <dependency>
                 <groupId>com.krt</groupId>
                 <artifactId>krt-plugin-upload</artifactId>
                 <version>${krt.version}</version>
             </dependency>
     	fastdfs依赖：
             <dependency>
                 <groupId>com.krt</groupId>
                 <artifactId>krt-file</artifactId>
                 <version>${krt.version}</version>
             </dependency>
             不能同时添加2个依赖
     
             支持多文件、文件续传、大文件切片 （ 续传、切片 目前只针对fastdfs集成）
     	秒传功能暂时不支持 后端已经支持该功能，等待前端完成文件md5功能
     
     2、优化代码生成器 替换新的上传功能 新增组件：上传头像
     3、用户管理新增头像功能
     4、系统新增彩色验证码（动态、静态）功能
     5、krt-plugins-xxxxx 命名为krt-plugin-xxxxx
     6、bug修改
     svn:https://192.168.1.251:9008/svn/krt251/admin/v3.0/tags/krt_1.0.5
     demo:http://192.168.1.186:8888/krt/
    
     1.0.5 优化升级
        修改mybatis-plus 源码发布了jar包 2.3.1.1 到 公司私服
        添加map转驼峰支持 无需编写javaconfig  在application.properties 配置
     
     
   1.0.5优化
     
     新增多数据源支持   
     pom添加依赖：
     <dependency>
         <groupId>com.baomidou</groupId>
         <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
         <version>${dynamic-datasource-spring-boot-starter.version}</version>
     </dependency>
     application-dev2.properties 里有多数据源配置demo  
     使用数据源
     在类（serviceImpl || Mapper）或方法上通过注解 @DS("数据源名称") eg:@DS("master")  指定  （方法上的优先级比类上要高）
     没有注解使用默认数据源（通过spring.datasource.dynamic.primary）

     1.0.6 优化升级 修复token 并发重复生成
     1.0.6 优化token验证，满足@KrtIgnoreAuth、@KrtLoginUser 同时使用 
     应用场景：
     传了accessToken就查询个人 没有查询全部
     @KrtIgnoreAuth
     @GetMapping("select")
     @ApiOperation(value = "查询demo")
     @ApiImplicitParams({
     		@ApiImplicitParam(paramType = "query", dataType = "String", name = "accessToken", value = "accessToken", required = false)
     })
     public xxxx select(@ApiIgnore @KrtLoginUser User user){
     	if(user==null){
     		//查询全部
     		return selectAll();
     	}else{
     		//查询个人
     		return selectByUserId(user.getId());
     	}
     } 
   
   krt_3.1.5 升级
     
     新增接口版本控制、整合swagger
     @KrtApiVersion接口版本注解 
     可以写在类、方法上  方法的优先级大于类
     如下：
     @KrtApiVersion(1)
     @RequestMapping("api/{version}/")
     public class ApiV1PushController {
     
        @PostMapping("test1")
        public ReturnBean test1(){
     	...
        }
     
        @KrtApiVersion(2)
        @PostMapping("test2")
        public ReturnBean test2(){
     	...
        }
     }
     
     访问地址：  
     api/v1/test1
     
     api/v2/test2
     	
     接口过期：设置deprecated = true （默认false）
     @KrtApiVersion(value = 1,deprecated = true)
     
     具体使用参考框架demo api模块  