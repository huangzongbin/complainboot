package com.krt.sys.controller;

import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.ReturnBean;
import com.krt.common.constant.GlobalConstant;
import com.krt.common.validator.Assert;
import com.krt.sys.entity.Res;
import com.krt.sys.service.IResService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 资源管理控制层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年7月19日
 */
@Slf4j
@Controller
public class ResController extends BaseController {

    @Autowired
    private IResService resService;

    /**
     * 资源管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("sys:res:list")
    @GetMapping("sys/res/list")
    public String list() {
        return "sys/res/list";
    }

    /**
     * 资源管理
     *
     * @param pid 父id
     * @return {@link List}
     */
    @RequiresPermissions("sys:res:list")
    @PostMapping("sys/res/list")
    @ResponseBody
    public ReturnBean list(Integer pid) {
        List list =  resService.selectByPid(pid);
        return ReturnBean.ok(list);
    }

    /**
     * 获取资源树数据
     *
     * @return {@link ReturnBean}
     */
    @PostMapping("sys/res/resTree")
    @ResponseBody
    public ReturnBean resTree() {
        List list = resService.selectAllTree();
        return ReturnBean.ok(list);
    }

    /**
     * 添加资源页
     *
     * @param pid 父id
     * @return {@link String}
     */
    @RequiresPermissions("sys:res:insert")
    @GetMapping("sys/res/insert")
    public String insert(Integer pid) {
        if (!Assert.isNull(pid)) {
            Res pRes = resService.selectById(pid);
            request.setAttribute("pRes", pRes);
        }
        return "sys/res/insert";
    }

    /**
     * 添加资源
     *
     * @param res 资源实体
     */
    @KrtLog("添加资源")
    @RequiresPermissions("sys:res:insert")
    @PostMapping("sys/res/insert")
    @ResponseBody
    public ReturnBean insert(Res res) {
        resService.insert(res);
        return ReturnBean.ok();
    }

    /**
     * 资源ICON页
     *
     * @return {@link String}
     */
    @GetMapping("sys/res/resIcon")
    public String resIcon() {
        return "sys/res/resIcon";
    }

    /**
     * 修改资源
     *
     * @param id 资源id
     * @return {@link String}
     */
    @RequiresPermissions("sys:res:update")
    @GetMapping("sys/res/update")
    public String update(Integer id) {
        Res res = resService.selectById(id);
        Integer pid = res.getPid();
        if (!GlobalConstant.DEFAULT_PID.equals(pid)) {
            Res pRes = resService.selectById(pid);
            request.setAttribute("pRes", pRes);
        }
        request.setAttribute("res", res);
        return "sys/res/update";
    }

    /**
     * 修改资源
     *
     * @param res 资源实体
     * @return {@link ReturnBean}
     */
    @KrtLog("修改资源")
    @RequiresPermissions("sys:res:update")
    @PostMapping("sys/res/update")
    @ResponseBody
    public ReturnBean update(Res res) {
        resService.updateById(res);
        return ReturnBean.ok();
    }

    /**
     * 删除资源
     *
     * @param id 资源id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除资源")
    @RequiresPermissions("sys:res:delete")
    @PostMapping("sys/res/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        resService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 资源信息页
     *
     * @param id 资源id
     * @return {@link String}
     */
    @RequiresPermissions("sys:res:see")
    @GetMapping("sys/res/see")
    public String see(Integer id) {
        Res res = resService.selectById(id);
        int pid = res.getPid();
        if (pid != GlobalConstant.DEFAULT_PID) {
            Res pRes = resService.selectById(pid);
            request.setAttribute("pRes", pRes);
        }
        request.setAttribute("res", res);
        return "sys/res/see";
    }
}
