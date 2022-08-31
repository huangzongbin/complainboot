package com.krt.api.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.krt.common.bean.DataTable;
import com.krt.common.bean.ReturnBean;
import com.krt.common.exception.KrtException;
import com.krt.common.validator.Assert;
import com.krt.common.validator.ValidatorUtils;
import com.krt.good.dto.GoodsDetailDTO;
import com.krt.good.entity.Goods;
import com.krt.good.entity.GoodsDetail;
import com.krt.good.service.IGoodsDetailService;
import com.krt.good.service.IGoodsService;

import com.krt.good.vo.GoodsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.apache.xmlbeans.impl.validator.ValidatorUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品添加接口
 */
@Api(tags = "4.商品接口")
@RestController
@RequestMapping("api/goods")
public class ApiGoodsController {

    @Resource
    private IGoodsService goodsService;

    @Resource
    private IGoodsDetailService goodsDetailService;
    /**
     *  查询商品首页信息列表
     * @param contentName
     * @param contentValue
     * @return
     */
    @PostMapping("list")
    @ApiOperation(value = "1.查询商品首页信息列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "accessToken", name = "accessToken", value = "鉴权token"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "contentName", value = "筛选类型 名称1，编号2，类型3,商家4"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "contentValue", value = "匹配的类型"),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageNo", value = "页数"),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "lenght", value = "长度"),
    })
    public ReturnBean listBill(String contentName, String contentValue, Integer pageNo, Integer lenght) {
        //Assert.isBlank(contentName, "搜索类型不能为空！");
        if(null == pageNo ){
            pageNo = 1;
        }
        if(null == lenght){
            lenght = 20;
        }
        Page page = new Page(pageNo, lenght);
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
        if(ObjectUtil.isNotEmpty(contentName)) {
            switch (contentName) {
                case "1":
                    wrapper.like(Goods::getName, contentValue);
                    break;
                case "2":
                    wrapper.like(Goods::getCode, contentValue);
                    break;
                case "3":
                    wrapper.eq(Goods::getType, contentValue);
                    break;
                case "4":
                    wrapper.like(Goods::getStoreName, contentValue);
                    break;
                default:
                    break;
            }
        }
        wrapper.orderByDesc(Goods::getInsertTime);
        IPage iPage = goodsService.selectPage(page, wrapper);
        DataTable ok = DataTable.ok(iPage);
        ok.setPageNum(iPage.getCurrent());
        return ReturnBean.ok(DataTable.ok(iPage));
    }




    /**
     *  查询商品首页信息列表
     * @return
     */
    @PostMapping("save")
    @ApiOperation(value = "2.保存商品信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "accessToken", name = "accessToken", value = "鉴权token"),
    })
    public ReturnBean save(@RequestBody GoodsDetailDTO saveBody) {

        try {
            ValidatorUtils.validateEntity(saveBody);

            GoodsDetail goodsDetail = new GoodsDetail();
            BeanUtils.copyProperties(saveBody,goodsDetail);
            goodsDetail.setPriceTotal(saveBody.getAmount().multiply(saveBody.getPrice()));
            goodsDetailService.insertEntry(goodsDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnBean.error(e.getMessage());
        }
        return ReturnBean.ok();
    }


   /* *//**
     *  保存商品详情
     * @return
     *//*
    @PostMapping("saveDetail")
    @ApiOperation(value = "2.保存商品信息详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "accessToken", name = "accessToken", value = "鉴权token"),
    })
    public ReturnBean saveDetail(@RequestBody GoodsDetailDTO saveBody) {

        try {
            ValidatorUtils.validateEntity(saveBody);

            GoodsDetail goodsDetail = new GoodsDetail();
            BeanUtils.copyProperties(saveBody,goodsDetail);
            goodsDetail.setPriceTotal(saveBody.getAmount().multiply(saveBody.getPrice()));
            goodsDetailService.insertEntry(goodsDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnBean.error(e.getMessage());
        }
        return ReturnBean.ok();
    }*/

    /**
     *  保存商品详情流水
     * @return
     */
    @PostMapping("saveDetailInfo")
    @ApiOperation(value = "2.1流水里面保存商品信息详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "accessToken", name = "accessToken", value = "鉴权token"),
    })
    public ReturnBean saveDetailInfo(@RequestBody GoodsDetailDTO saveBody) {

        try {
            ValidatorUtils.validateEntity(saveBody);

            GoodsDetail goodsDetail = new GoodsDetail();
            BeanUtils.copyProperties(saveBody,goodsDetail);
            goodsDetail.setPriceTotal(saveBody.getAmount().multiply(saveBody.getPrice()));
            goodsDetailService.insertDetail(goodsDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnBean.error(e.getMessage());
        }
        return ReturnBean.ok();
    }

    /**
     *  查询商品首页信息列表
     * @return
     */
    @PostMapping("getDetail")
    @ApiOperation(value = "3.查看总详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "accessToken", name = "accessToken", value = "鉴权token"),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "goodId", value = "商品id"),
    })
    public ReturnBean getDetail(Integer goodId) {
        Goods goods = goodsService.selectById(goodId);
        if(ObjectUtil.isNull(goods)){
            return ReturnBean.error("数据传输错误！");
        }
        List<GoodsDetail> goodsDetails = goodsDetailService.selectList(new LambdaQueryWrapper<GoodsDetail>().eq(GoodsDetail::getPid, goods.getId()));
        GoodsVO goodsVO = new GoodsVO();
        BeanUtils.copyProperties(goods,goodsVO);
        goodsVO.setDetails(goodsDetails);
        return ReturnBean.ok(goodsVO);
    }


    /**
     *  修改详情
     * @return
     */
    @PostMapping("updateDetail")
    @ApiOperation(value = "4.修改详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "accessToken", name = "accessToken", value = "鉴权token"),
    })
    public ReturnBean updateDetail(@RequestBody GoodsDetailDTO saveBody) {

        // 修改只可以修改商品的数量，价格，
        Validator.validateNotNull(saveBody.getId(),"修改id必须传");

        GoodsDetail goodsDetail = goodsDetailService.selectById(saveBody.getId());
        Validator.validateNotNull(goodsDetail,"修改id必须传");
        GoodsDetail updateDetail = new GoodsDetail();
        BeanUtils.copyProperties(saveBody,updateDetail);
        updateDetail.setPid(goodsDetail.getPid());
        goodsDetailService.updateEntry(updateDetail);
        return ReturnBean.ok();
    }


    /**
     *  删除物品
     * @return
     */
    @PostMapping("deleteGood")
    @ApiOperation(value = "5.删除物品", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "accessToken", name = "accessToken", value = "鉴权token"),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "id", value = "商品id"),
    })
    public ReturnBean deleteGoods(Integer id) {

        // 修改只可以修改商品的数量，价格，
        Validator.validateNotNull(id,"修改id必须传");
        Goods goods = goodsService.selectById(id);
        Validator.validateNotNull(goods,"修改id参数错误");
        goodsService.deleteEntry(goods);
        return ReturnBean.ok();
    }


    /**
     *  删除详情
     * @return
     */
    @PostMapping("deleteDetail")
    @ApiOperation(value = "6.删除详情", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",  name = "accessToken", value = "鉴权token"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "id", value = "流水详情id")
    })
    public ReturnBean deleteDetail(Integer id) {

        // 修改只可以修改商品的数量，价格，
        Validator.validateNotNull(id,"修改id必须传");

        GoodsDetail goodsDetail = goodsDetailService.selectById(id);
        Validator.validateNotNull(goodsDetail,"修改id必须传");
        goodsDetailService.deleteEntry(goodsDetail);
        return ReturnBean.ok();
    }

}
