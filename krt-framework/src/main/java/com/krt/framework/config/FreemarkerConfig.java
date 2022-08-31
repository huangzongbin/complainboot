package com.krt.framework.config;

import com.jagregory.shiro.freemarker.ShiroTags;
import com.krt.framework.tag.DicTag;
import freemarker.template.Configuration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * freemarker初始化
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年06月29日
 */
@org.springframework.context.annotation.Configuration
public class FreemarkerConfig implements InitializingBean {

    @Autowired
    private Configuration configuration;

    @Autowired
    private DicTag dicTag;

    @Override
    public void afterPropertiesSet() throws Exception {
        // shiro标签
        configuration.setSharedVariable("shiro", new ShiroTags());
        //标签名与标签类
        configuration.setSharedVariable("dic", dicTag);
    }

}
