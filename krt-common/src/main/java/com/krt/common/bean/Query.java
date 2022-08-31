package com.krt.common.bean;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krt.common.constant.GlobalConstant;
import lombok.Data;

import java.util.Map;

/**
 * 查询bean
 *
 * @author 殷帅
 * @version 1.0
 */
@Data
public class Query<T> {

    /**
     * 排序字段
     */
    private final String ORDER_NAME = "orderName";

    /**
     * 排序方向
     */
    private final String ORDER_TYPE = "orderType";

    /**
     * 升序
     */
    private final String ASC = "asc";

    /**
     * 分页对象
     */
    private Page<T> page;

    /**
     * 构造分页参数
     *
     * @param para 搜索参数
     * @return {@link Query}
     */
    public Query(Map para) {
        Long start = Long.valueOf(para.get(GlobalConstant.START).toString());
        Long lenght = Long.valueOf(para.get(GlobalConstant.LENGTH).toString());
        Long pageNo = start / lenght + 1;
        this.page = new Page(pageNo, lenght);
        if (para.containsKey(ORDER_NAME) && para.containsKey(ORDER_TYPE)) {
            String field = para.get(ORDER_NAME).toString();
            String order = para.get(ORDER_TYPE).toString();
            if (ASC.equalsIgnoreCase(order)) {
                this.page.setAsc(field);
            } else {
                this.page.setDesc(field);
            }
        }
    }
}
