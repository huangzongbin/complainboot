package com.krt.sys.service.impl;

import com.krt.common.base.BaseServiceImpl;
import com.krt.common.validator.Assert;
import com.krt.sys.entity.Number;
import com.krt.sys.mapper.NumberMapper;
import com.krt.sys.service.INumberService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * 流水号服务实现层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年05月17日
 */
@Service
public class NumberServiceImpl extends BaseServiceImpl<NumberMapper, Number> implements INumberService {

    /**
     * 添加流水号规则
     *
     * @param number 流水号
     */
    @Override
    public boolean insert(Number number) {
        String formatNum = getFormatNum(number.getNum(), number);
        number.setFormatNum(formatNum);
        return super.insert(number);
    }

    /**
     * 删除
     *
     * @param id 规则id
     * @return true 删除成功 false 删除失败
     */
    @Override
    public boolean deleteById(Serializable id) {
        return super.deleteById(id);
    }

    /**
     * 更新
     *
     * @param number 规则
     * @return true 删除成功 false 删除失败
     */
    @Override
    public boolean updateById(Number number) {
        String formatNum = getFormatNum(number.getNum(), number);
        number.setFormatNum(formatNum);
        return super.updateById(number);
    }


    /**
     * 获取流水号
     *
     * @param code 通过数据库行级锁获取流水号
     * @return 流水号
     */
    @Override
    public synchronized String createNum(String code) {
        Number number = baseMapper.selectByCodeForUpdate(code);
        String dateFormat = number.getDateFormat();
        String currentDate;
        Integer oldNum;
        //有日期格式化规则
        if (!Assert.isBlank(dateFormat)) {
            currentDate = baseMapper.selectCurrentDate(dateFormat);
            oldNum = baseMapper.selectNum(code, dateFormat, currentDate);
        } else {
            oldNum = baseMapper.selectNumByCode(code);
        }
        if (oldNum == null) {
            oldNum = 0;
        }
        String formatNum = getFormatNum(oldNum + 1, number);
        number.setNum(oldNum + 1);
        number.setFormatNum(formatNum);
        updateById(number);
        return formatNum;
    }

    /**
     * 获取格式化号码
     *
     * @param oldNum 老号码
     * @param number 流水号规则
     * @return 格式化流水号
     */
    private String getFormatNum(Integer oldNum, Number number) {
        String dateFormat = number.getDateFormat();
        String currentDate;
        //有日期格式化规则
        if (!Assert.isBlank(dateFormat)) {
            currentDate = baseMapper.selectCurrentDate(dateFormat);
        } else {
            //无日志格式化
            currentDate = "";
        }
        //根据长度格式化 不足补0
        String newNum = String.format("%0" + number.getNumLength() + "d", oldNum);
        String formatNum = number.getPrefix() + currentDate + newNum + number.getSuffix();
        return formatNum;
    }
}
