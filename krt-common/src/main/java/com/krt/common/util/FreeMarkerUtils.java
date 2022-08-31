package com.krt.common.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * FreeMarker工具类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年9月26日
 */
@Slf4j
public class FreeMarkerUtils {

    /**
     * 模板渲染并返回内容
     *
     * @param root
     * @param pathPrefix
     * @param ftlName
     */
    public static String getTemplateContent(Map root, String pathPrefix, String ftlName) {
        StringWriter out = null;
        try {
            Configuration cfg = new Configuration(Configuration.getVersion());
            cfg.setClassForTemplateLoading(FreeMarkerUtils.class, pathPrefix);
            cfg.setDefaultEncoding("UTF-8");
            Template template = cfg.getTemplate(ftlName);
            out = new StringWriter();
            template.process(root, out);
            return out.toString();
        } catch (Exception e) {
            log.debug("FreeMarker读取模板异常", e);
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
