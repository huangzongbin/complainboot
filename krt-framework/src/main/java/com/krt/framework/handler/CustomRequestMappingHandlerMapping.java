package com.krt.framework.handler;

import com.krt.common.annotation.KrtApiVersion;
import com.krt.framework.condition.KrtApiVersionCondition;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * 版本号匹配拦截器
 *
 * @author 殷帅
 * @version 1.0
 * @date 2019年06月03日
 */
public class CustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping {


    /**
     * 构建类级的映射要求
     *
     * @param handlerType 方法
     * @return RequestCondition
     */
    @Override
    protected RequestCondition<KrtApiVersionCondition> getCustomTypeCondition(Class<?> handlerType) {
        KrtApiVersion krtApiVersion = AnnotationUtils.findAnnotation(handlerType, KrtApiVersion.class);
        return createCondition(krtApiVersion);
    }

    /**
     * 构建方法级的映射要求
     *
     * @param method 方法
     * @return RequestCondition
     */
    @Override
    protected RequestCondition<KrtApiVersionCondition> getCustomMethodCondition(Method method) {
        KrtApiVersion krtApiVersion = AnnotationUtils.findAnnotation(method, KrtApiVersion.class);
        return createCondition(krtApiVersion);
    }

    /**
     * 实例化RequestCondition
     *
     * @param krtApiVersion 注解
     * @return RequestCondition
     */
    private RequestCondition<KrtApiVersionCondition> createCondition(KrtApiVersion krtApiVersion) {
        return krtApiVersion == null ? null : new KrtApiVersionCondition(krtApiVersion);
    }
}
