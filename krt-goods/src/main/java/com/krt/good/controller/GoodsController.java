package com.krt.good.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.DataTable;
import com.krt.common.bean.ReturnBean;
import com.krt.good.entity.Goods;
import com.krt.good.entity.GoodsDetail;
import com.krt.good.service.IGoodsDetailService;
import com.krt.good.service.IGoodsService;

import com.krt.good.vo.GoodsVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 商品信息控制层
 *
 * @author hzb
 * @version 1.0
 * @date 2022年04月09日
 */
@Controller
public class GoodsController extends BaseController {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IGoodsDetailService goodsDetailService;

    /**
     * 商品信息管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("goods:goods:list")
    @GetMapping("goods/goods/list")
    public String list() {
        return "goods/goods/list";
    }

    /**
     * 商品信息管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("goods:goods:list")
    @PostMapping("goods/goods/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = goodsService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 新增商品信息页
     *
     * @return {@link String}
     */
    @RequiresPermissions("goods:goods:insert")
    @GetMapping("goods/goods/insert")
    public String insert() {
        return "goods/goods/insert";
    }

    /**
     * 添加商品信息
     *
     * @param goods 商品信息
     * @return {@link ReturnBean}
     */
    @KrtLog("添加商品信息")
    @RequiresPermissions("goods:goods:insert")
    @PostMapping("goods/goods/insert")
    @ResponseBody
    public ReturnBean insert(Goods goods) {
        goodsService.insert(goods);
        return ReturnBean.ok();
    }

    /**
     * 修改商品信息页
     *
     * @param id 商品信息id
     * @return {@link String}
     */
    @RequiresPermissions("goods:goods:update")
    @GetMapping("goods/goods/update")
    public String update(Integer id) {
        Goods goods = goodsService.selectById(id);
        request.setAttribute("goods", goods);
        return "goods/goods/update";
    }

    /**
     * 修改商品信息
     *
     * @param goods 商品信息
     * @return {@link ReturnBean}
     */
    @KrtLog("修改商品信息")
    @RequiresPermissions("goods:goods:update")
    @PostMapping("goods/goods/update")
    @ResponseBody
    public ReturnBean update(Goods goods) {
        goodsService.updateById(goods);
        return ReturnBean.ok();
    }

    /**
     * 修改商品信息页
     *
     * @param id 商品信息id
     * @return {@link String}
     */
    @RequiresPermissions("goods:goods:update")
    @GetMapping("goods/goods/see")
    public String see(Integer id) {
        Goods goods = goodsService.selectById(id);
        request.setAttribute("goods", goods);
        List<GoodsDetail> goodsDetails = goodsDetailService.selectList(new LambdaQueryWrapper<GoodsDetail>().eq(GoodsDetail::getPid, goods.getId()));
        request.setAttribute("goodsDetails", goodsDetails);
        return "goods/goods/see";
    }

    /**
     * 删除商品信息
     *
     * @param id 商品信息id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除商品信息")
    @RequiresPermissions("goods:goods:delete")
    @PostMapping("goods/goods/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        goodsService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除商品信息
     *
     * @param ids 商品信息ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除商品信息")
    @RequiresPermissions("goods:goods:delete")
    @PostMapping("goods/goods/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        goodsService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }


}
