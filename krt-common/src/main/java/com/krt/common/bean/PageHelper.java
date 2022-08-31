package com.krt.common.bean;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.core.NamedThreadLocal;

/**
 * 分页工具类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2019年01月02日
 */
public class PageHelper {

    /**
     * 分页参数
     */
    private static final ThreadLocal<IPage> LOCAL_PAGE = new NamedThreadLocal<>("ThreadLocal PageHelper");

    public PageHelper() {

    }

    /**
     * 获取分页参数
     *
     * @return {@link IPage}
     */
    public static IPage getPage() {
        return LOCAL_PAGE.get();
    }

    /**
     * 开始设置分页参数
     */
    public static void startPage(Page page) {
        LOCAL_PAGE.set(page);
    }

    /**
     * 移除分页参数
     */
    public static void remove() {
        LOCAL_PAGE.remove();
    }
}
