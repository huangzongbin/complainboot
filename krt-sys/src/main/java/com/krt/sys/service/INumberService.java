package com.krt.sys.service;

import com.krt.sys.entity.Number;
import com.krt.common.base.IBaseService;

/**
 * 流水号服务接口层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年05月17日
 */
public interface INumberService extends IBaseService<Number> {

    /**
     * 生成流水号
     *
     * @param code 通过数据库行级锁获取流水号
     * @return {@link String} 流水号
     */
    String createNum(String code);

}
