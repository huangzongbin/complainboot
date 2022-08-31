package com.krt.good.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.krt.good.dto.GoodsDetailDTO;
import com.krt.good.entity.GoodsDetail;
import com.krt.good.service.IGoodsDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.krt.good.entity.Goods;
import com.krt.good.mapper.GoodsMapper;
import com.krt.good.service.IGoodsService;
import com.krt.common.base.BaseServiceImpl;

import javax.annotation.Resource;


/**
 * 商品信息服务接口实现层
 *
 * @author hzb
 * @version 1.0
 * @date 2022年04月09日
 */
@Service
public class GoodsServiceImpl extends BaseServiceImpl<GoodsMapper, Goods> implements IGoodsService {


    @Autowired
    private IGoodsDetailService goodsDetailService;

    @Override
    public void deleteEntry(Goods goods) {
        baseMapper.deleteById(goods.getId());
        goodsDetailService.delete(new LambdaUpdateWrapper<GoodsDetail>().eq(GoodsDetail::getPid,goods.getId()));
    }
}
