package com.krt.gen.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 代码生成器配置
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月11日
 */
@Data
@Component
@ConfigurationProperties(prefix = "gen")
public class GenProperties {

    /**
     * 数据库类型
     */
    private String dbType = "mysql";

    /**
     * 模板加载路径
     */
    private String templateLoaderPath = "/gen";

    /**
     * 包前缀
     */
    private String packagePrefix = "com.krt.";

    /**
     * java源码生成路径
     */
    private String javaPath = "\\src\\main\\java";

    /**
     * 视图生成路径
     */
    private String viewPath = "\\src\\main\\resources\\templates";

}
