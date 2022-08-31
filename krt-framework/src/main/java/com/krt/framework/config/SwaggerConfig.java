package com.krt.framework.config;

import com.krt.common.bean.ReturnCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;


/**
 * 初始化Swagger
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年4月18日
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${web.swagger-enable}")
    private boolean swaggerEnable;

    /**
     * 添加自定义异常信息
     */
    private ArrayList<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>() {
        private static final long serialVersionUID = 1L;
        {
            add(new ResponseMessageBuilder().code(ReturnCode.OK.getCode()).message(ReturnCode.OK.getMsg()).build());
            for (ReturnCode rc : ReturnCode.values()) {
                if (rc.getCode() != 200) {
                    add(new ResponseMessageBuilder().code(rc.getCode()).message(rc.getMsg()).responseModel(new ModelRef("Error")).build());
                }
            }
        }
    };


    /**
     * Docket配置
     */
    @Bean
    public Docket appApi() {
        //自定义异常信息
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerEnable)
                .groupName("appApi")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.krt.api.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalResponseMessage(RequestMethod.GET, responseMessages)
                .globalResponseMessage(RequestMethod.POST, responseMessages)
                .globalResponseMessage(RequestMethod.PUT, responseMessages)
                .globalResponseMessage(RequestMethod.DELETE, responseMessages)
                .globalResponseMessage(RequestMethod.PATCH, responseMessages)
                .apiInfo(appApiInfo());
    }

    /**
     * 配置文档说明信息
     *
     * @return ApiInfo
     */
    private ApiInfo appApiInfo() {
        StringBuffer sb = new StringBuffer(1024);
        sb.append("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;以下是平台对外开放的所有接口。<br>");
        return new ApiInfoBuilder()
                .title("平台API接口")
                .description(sb.toString())
                .version("0.0.1")
                .build();
    }

}