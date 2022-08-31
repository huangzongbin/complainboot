package com.krt.framework.condition;

import com.krt.common.annotation.KrtApiVersion;
import com.krt.common.bean.ReturnBean;
import com.krt.common.exception.KrtException;
import lombok.Data;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 自定义请求条件
 *
 * @author 殷帅
 * @version 1.0
 * @date 2019年06月03日
 */
@Data
public class KrtApiVersionCondition implements RequestCondition<KrtApiVersionCondition> {

    /**
     * 路径中版本的前缀， 这里用 /v[1-9]/的形式
     */
    private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile("v(\\d+)/");

    /**
     * api的版本
     */
    private KrtApiVersion krtApiVersion;

    public KrtApiVersionCondition(KrtApiVersion krtApiVersion) {
        this.krtApiVersion = krtApiVersion;
    }


    /**
     * 将不同的筛选条件合并
     *
     * @param krtApiVersionCondition 版本条件
     * @return KrtApiVersionCondition
     */
    @Override
    public KrtApiVersionCondition combine(KrtApiVersionCondition krtApiVersionCondition) {
        // 采用最后定义优先原则，则方法上的定义覆盖类上面的定义
        return new KrtApiVersionCondition(krtApiVersionCondition.getKrtApiVersion());
    }


    /**
     * 根据request查找匹配到的筛选条件
     *
     * @param request 请求
     * @return KrtApiVersionCondition
     */
    @Override
    public KrtApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        Matcher m = VERSION_PREFIX_PATTERN.matcher(request.getRequestURI());
        if (m.find()) {
            Integer version = Integer.valueOf(m.group(1));
            if (version >= this.krtApiVersion.value()) {
                if (this.krtApiVersion.deprecated()) {
                    throw new KrtException(ReturnBean.error("当前版本已停用，请升级到最新版本。"));
                }
                return this;
            }
        }
        return null;
    }

    /**
     * 不同筛选条件比较,用于排序
     *
     * @param krtApiVersionCondition 版本条件
     * @param request                请求
     * @return int
     */
    @Override
    public int compareTo(KrtApiVersionCondition krtApiVersionCondition, HttpServletRequest request) {
        // 优先匹配最新的版本号
        return krtApiVersionCondition.getKrtApiVersion().value() - this.krtApiVersion.value();
    }
}
