package com.krt.file.fastdfs.config;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * Fastdfs初始化
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年09月10日
 */
@Configuration
@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@ConditionalOnProperty(name = "web.upload-type", havingValue = "fastdfs")
public class FastdfsConfig {

}
