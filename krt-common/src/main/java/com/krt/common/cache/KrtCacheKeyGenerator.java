package com.krt.common.cache;

import com.krt.common.constant.GlobalConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 * 自定义缓存key生成器
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年06月20日
 */
@Slf4j
public class KrtCacheKeyGenerator implements KeyGenerator {


    /**
     * key 为包名+方法名+：+参数
     *
     * @param target 目标资源
     * @param method 方法名
     * @param params 参数
     * @return {@link Object}
     */
    @Override
    public Object generate(Object target, Method method, Object... params) {
        boolean isFirst = true;
        StringBuilder sb = new StringBuilder();
        sb.append(target.getClass().getName());
        sb.append(GlobalConstant.POINT);
        sb.append(method.getName());
        if (params != null && params.length > 0) {
            sb.append(GlobalConstant.COLON);
            for (Object obj : params) {
                if (!isFirst) {
                    sb.append(GlobalConstant.POINT);
                    isFirst = false;
                }
                sb.append(obj.toString());
            }
        }
        return sb.toString();
    }
}
