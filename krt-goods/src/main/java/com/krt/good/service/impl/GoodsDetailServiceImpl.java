package com.krt.good.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.krt.common.bean.ReturnBean;
import com.krt.common.exception.KrtException;
import com.krt.good.entity.Goods;
import com.krt.good.enums.CreateEnum;
import com.krt.good.service.IGoodsService;
import org.springframework.stereotype.Service;
import com.krt.good.entity.GoodsDetail;
import com.krt.good.mapper.GoodsDetailMapper;
import com.krt.good.service.IGoodsDetailService;
import com.krt.common.base.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.security.KeyException;
import java.util.Date;
import java.util.List;


/**
 * 商品信息服务接口实现层
 *
 * @author hzb
 * @version 1.0
 * @date 2022年04月12日
 */
@Service
public class GoodsDetailServiceImpl extends BaseServiceImpl<GoodsDetailMapper, GoodsDetail> implements IGoodsDetailService {

    @Resource
    private IGoodsService goodsService;

    /**
     * 新增
     *
     * @param saveBody
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertEntry(GoodsDetail saveBody) {

        String code = saveBody.getCode();
        String name = saveBody.getName();
        String store = saveBody.getStore();
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Goods::getStore, store).eq(Goods::getName, name)
                .eq(Goods::getCode, code)
                .orderByAsc(Goods::getId)
                .last("limit 1");
        Goods goods = goodsService.selectOne(wrapper);


        if (ObjectUtil.isNotNull(goods)) {
            throw new KrtException(ReturnBean.error("同一个直播已经存在同一个编号和名称的衣服！"));
        }

        goods = new Goods();
        goods.setCode(code);
        goods.setName(name);
        goods.setAmount(saveBody.getAmount());
        goods.setType(saveBody.getType());
        goods.setStoreName(saveBody.getStoreName());
        goods.setStore(saveBody.getStore());
        goods.setTotalPrice(saveBody.getPriceTotal());
        goods.setPhotoUrl(saveBody.getPhotoUrl());
        goods.setInsertTime(new Date());
        goodsService.insert(goods);

        /*BigDecimal amount = goods.getAmount();
        BigDecimal totalPrice = goods.getTotalPrice();
        if (CreateEnum.INCREATE.getCode().equals(saveBody.getCreaseType())) {
            goods.setAmount(amount.add(saveBody.getAmount()));
            goods.setTotalPrice(totalPrice.add(saveBody.getPriceTotal()));
        } else {
            goods.setAmount(goods.getAmount().subtract(saveBody.getAmount()));
            goods.setTotalPrice(goods.getTotalPrice().subtract(saveBody.getPriceTotal()));

        }
        goods.setUpdateTime(new Date());
        goodsService.updateById(goods);*/

        saveBody.setPid(goods.getId());
        baseMapper.insert(saveBody);
    }

    /**
     * 新增
     *
     * @param saveBody
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertDetail(GoodsDetail saveBody) {

        String code = saveBody.getCode();
        String name = saveBody.getName();
        String store = saveBody.getStore();
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Goods::getStore, store).eq(Goods::getName, name)
                .eq(Goods::getCode, code)
                .orderByAsc(Goods::getId)
                .last("limit 1");
        Goods goods = goodsService.selectOne(wrapper);

        if (ObjectUtil.isNull(goods)) {
            throw new KrtException(ReturnBean.error("未添加库存"));
        }

        BigDecimal amount = goods.getAmount();
        BigDecimal totalPrice = goods.getTotalPrice();

        if (CreateEnum.INCREATE.getCode().equals(saveBody.getCreaseType())) {
            goods.setAmount(amount.add(saveBody.getAmount()));
            goods.setTotalPrice(totalPrice.add(saveBody.getPriceTotal()));
        } else {
            goods.setAmount(amount.subtract(saveBody.getAmount()));
            goods.setTotalPrice(totalPrice.subtract(saveBody.getPriceTotal()));
        }
        goods.setUpdateTime(new Date());
        goodsService.updateById(goods);

        saveBody.setPid(goods.getId());
        baseMapper.insert(saveBody);
    }

    /**
     * 更新
     *
     * @param updateDetail
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEntry(GoodsDetail updateDetail) {
        updateDetail.setPriceTotal(updateDetail.getAmount().multiply(updateDetail.getPrice()));
        baseMapper.updateById(updateDetail);
        updateGoods(updateDetail);
    }

    /**
     * 删除
     *
     * @param goodsDetail
     */
    @Override
    public void deleteEntry(GoodsDetail goodsDetail) {
        baseMapper.deleteById(goodsDetail.getId());
        updateGoods(goodsDetail);
    }

    /**
     * 更新主商品
     *
     * @param updateDetail
     */
    private void updateGoods(GoodsDetail updateDetail) {
        // 更新商品数量

        List<GoodsDetail> goodsDetails = baseMapper.selectList(new LambdaQueryWrapper<GoodsDetail>().eq(GoodsDetail::getPid, updateDetail.getPid()));
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (GoodsDetail goodsDetail : goodsDetails) {
            if (CreateEnum.INCREATE.getCode().equals(goodsDetail.getCreaseType())) {
                amount = amount.add(goodsDetail.getAmount());
                totalPrice = totalPrice.add(goodsDetail.getPriceTotal());
            } else {
                amount = amount.subtract(goodsDetail.getAmount());
                totalPrice = totalPrice.subtract(goodsDetail.getPriceTotal());
            }
        }
        Goods goods = new Goods();
        goods.setAmount(amount);
        goods.setTotalPrice(totalPrice);
        goods.setId(updateDetail.getPid());
        goodsService.updateById(goods);
    }
}
