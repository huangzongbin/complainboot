package com.krt.good.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.DataTable;
import com.krt.common.bean.ReturnBean;
import com.krt.good.entity.GoodsDetail;
import com.krt.good.service.IGoodsDetailService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;

/**
 * 商品信息控制层
 *
 * @author hzb
 * @version 1.0
 * @date 2022年04月12日
 */
@Controller
public class GoodsDetailController extends BaseController {

    @Autowired
    private IGoodsDetailService goodsDetailService;

    /**
     * 商品信息管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("goodsDetail:goodsDetail:list")
    @GetMapping("goodsDetail/goodsDetail/list")
    public String list() {
        return "goodsDetail/goodsDetail/list";
    }

    /**
     * 商品信息管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("goodsDetail:goodsDetail:list")
    @PostMapping("goodsDetail/goodsDetail/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = goodsDetailService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 新增商品信息页
     *
     * @return {@link String}
     */
    @RequiresPermissions("goodsDetail:goodsDetail:insert")
    @GetMapping("goodsDetail/goodsDetail/insert")
    public String insert() {
        return "goodsDetail/goodsDetail/insert";
    }

    /**
     * 添加商品信息
     *
     * @param goodsDetail 商品信息
     * @return {@link ReturnBean}
     */
    @KrtLog("添加商品信息")
    @RequiresPermissions("goodsDetail:goodsDetail:insert")
    @PostMapping("goodsDetail/goodsDetail/insert")
    @ResponseBody
    public ReturnBean insert(GoodsDetail goodsDetail) {
        goodsDetailService.insert(goodsDetail);
        return ReturnBean.ok();
    }

    /**
     * 修改商品信息页
     *
     * @param id 商品信息id
     * @return {@link String}
     */
    @RequiresPermissions("goodsDetail:goodsDetail:update")
    @GetMapping("goodsDetail/goodsDetail/update")
    public String update(Integer id) {
        GoodsDetail goodsDetail = goodsDetailService.selectById(id);
        request.setAttribute("goodsDetail", goodsDetail);
        return "goodsDetail/goodsDetail/update";
    }

    /**
     * 修改商品信息
     *
     * @param goodsDetail 商品信息
     * @return {@link ReturnBean}
     */
    @KrtLog("修改商品信息")
    @RequiresPermissions("goodsDetail:goodsDetail:update")
    @PostMapping("goodsDetail/goodsDetail/update")
    @ResponseBody
    public ReturnBean update(GoodsDetail goodsDetail) {
        goodsDetailService.updateById(goodsDetail);
        return ReturnBean.ok();
    }

    /**
     * 删除商品信息
     *
     * @param id 商品信息id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除商品信息")
    @RequiresPermissions("goodsDetail:goodsDetail:delete")
    @PostMapping("goodsDetail/goodsDetail/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        goodsDetailService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除商品信息
     *
     * @param ids 商品信息ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除商品信息")
    @RequiresPermissions("goodsDetail:goodsDetail:delete")
    @PostMapping("goodsDetail/goodsDetail/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        goodsDetailService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }


}
