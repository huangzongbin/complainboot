package com.krt.oa.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.DataTable;
import com.krt.common.bean.ReturnBean;
import com.krt.common.session.SessionUser;
import com.krt.common.util.ShiroUtils;
import com.krt.oa.entity.OaNotice;
import com.krt.oa.service.IOaNoticeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Arrays;
import java.util.Map;

/**
 * 通知公告表控制层
 *
 * @author 转移
 * @version 1.0
 * @date 2019年06月10日
 */
@Controller
public class OaNoticeController extends BaseController {

    @Autowired
    private IOaNoticeService oaNoticeService;

    /**
     * 通知公告表管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("oa:notice:oaNotice:list")
    @GetMapping("oa/oaNotice/list")
    public String list() {
        return "oa/oaNotice/list";
    }

    /**
     * 通知公告表管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("oa:notice:oaNotice:list")
    @PostMapping("oa/oaNotice/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = oaNoticeService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 新增通知公告表页
     *
     * @return {@link String}
     */
    @RequiresPermissions("oa:notice:oaNotice:insert")
    @GetMapping("oa/oaNotice/insert")
    public String insert() {
        SessionUser user = ShiroUtils.getSessionUser();
        request.setAttribute("currentUser",user);
        return "oa/oaNotice/insert";
    }

    /**
     * 添加通知公告表
     *
     * @param oaNotice 通知公告表
     * @return {@link ReturnBean}
     */
    @KrtLog("添加通知公告表")
    @RequiresPermissions("oa:notice:oaNotice:insert")
    @PostMapping("oa/oaNotice/insert")
    @ResponseBody
    public ReturnBean insert(OaNotice oaNotice) {
        oaNoticeService.insert(oaNotice);
        return ReturnBean.ok();
    }

    /**
     * 修改通知公告表页
     *
     * @param id 通知公告表id
     * @return {@link String}
     */
    @RequiresPermissions("oa:notice:oaNotice:update")
    @GetMapping("oa/oaNotice/update")
    public String update(Integer id) {
        OaNotice oaNotice = oaNoticeService.selectById(id);
        request.setAttribute("oaNotice", oaNotice);
        SessionUser user = ShiroUtils.getSessionUser();
        request.setAttribute("currentUser",user);
        return "oa/oaNotice/update";
    }

    /**
     * 修改通知公告表
     *
     * @param oaNotice 通知公告表
     * @return {@link ReturnBean}
     */
    @KrtLog("修改通知公告表")
    @RequiresPermissions("oa:notice:oaNotice:update")
    @PostMapping("oa/oaNotice/update")
    @ResponseBody
    public ReturnBean update(OaNotice oaNotice) {
        oaNoticeService.updateById(oaNotice);
        return ReturnBean.ok();
    }

    /**
     * 删除通知公告表
     *
     * @param id 通知公告表id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除通知公告表")
    @RequiresPermissions("oa:notice:oaNotice:delete")
    @PostMapping("oa/oaNotice/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        oaNoticeService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除通知公告表
     *
     * @param ids 通知公告表ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除通知公告表")
    @RequiresPermissions("oa:notice:oaNotice:delete")
    @PostMapping("oa/oaNotice/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        oaNoticeService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }


}
