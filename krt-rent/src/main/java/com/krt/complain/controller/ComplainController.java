package com.krt.complain.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.DataTable;
import com.krt.common.bean.ReturnBean;
import com.krt.common.util.DateUtils;
import com.krt.common.util.ShiroUtils;
import com.krt.complain.entity.Complain;
import com.krt.complain.service.IComplainService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 投诉信息控制层
 *
 * @author 转移
 * @version 1.0
 * @date 2019年06月10日
 */
@Controller
public class ComplainController extends BaseController {

    @Autowired
    private IComplainService complainService;

    /**
     * 投诉信息管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("complain:complain:list")
    @GetMapping("complain/complain/list")
    public String list() {
        String year = DateUtils.getDate("yyyy");
        request.setAttribute("year",year);
        return "complain/list";
    }

    /**
     * 投诉信息管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("complain:complain:list")
    @PostMapping("complain/complain/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = complainService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 新增投诉信息页
     *
     * @return {@link String}
     */
    @RequiresPermissions("complain:complain:insert")
    @GetMapping("complain/complain/insert")
    public String insert() {
        return "complain/insert";
    }

    /**
     * 添加投诉信息
     *
     * @param complain 投诉信息
     * @return {@link ReturnBean}
     */
    @KrtLog("添加投诉信息")
    @RequiresPermissions("complain:complain:insert")
    @PostMapping("complain/complain/insert")
    @ResponseBody
    public ReturnBean insert(Complain complain) {
        complainService.insertAndMsgLog(complain);
        return ReturnBean.ok();
    }

    /**
     * 修改投诉信息页
     *
     * @param id 投诉信息id
     * @return {@link String}
     */
    @RequiresPermissions("complain:complain:update")
    @GetMapping("complain/complain/update")
    public String update(Integer id) {
        Complain complain = complainService.selectById(id);
        request.setAttribute("complain", complain);
        request.setAttribute("cpnImgs", CollectionUtils.arrayToList(complain.getCpnImg().split(",")));
        request.setAttribute("currentUser", ShiroUtils.getSessionUser());
        return "complain/update";
    }

    @GetMapping("complain/complain/see")
    public String see(Integer id) {
        Complain complain = complainService.selectById(id);
        request.setAttribute("complain", complain);
        request.setAttribute("cpnImgs", CollectionUtils.arrayToList(complain.getCpnImg().split(",")));
        request.setAttribute("currentUser", ShiroUtils.getSessionUser());
        return "complain/see";
    }

    /**
     * 修改投诉信息
     *
     * @param complain 投诉信息
     * @return {@link ReturnBean}
     */
    @KrtLog("修改投诉信息")
    @RequiresPermissions("complain:complain:update")
    @PostMapping("complain/complain/update")
    @ResponseBody
    public ReturnBean update(Complain complain) {
        complain.setOptTime(new Date());
        complainService.updateByIdAndMsgLog(complain);
        return ReturnBean.ok();
    }

    /**
     * 删除投诉信息
     *
     * @param id 投诉信息id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除投诉信息")
    @RequiresPermissions("complain:complain:delete")
    @PostMapping("complain/complain/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        complainService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除投诉信息
     *
     * @param ids 投诉信息ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除投诉信息")
    @RequiresPermissions("complain:complain:delete")
    @PostMapping("complain/complain/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        complainService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }


}
