package com.krt.sys.controller;

import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.DataTable;
import com.krt.common.bean.ReturnBean;
import com.krt.sys.service.ISessionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 在线用户管理控制层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年10月25日
 */
@Slf4j
@Controller
public class SessionController extends BaseController {

    @Autowired
    private ISessionService sessionService;

    /**
     * 在线用户管理页
     */
    @RequiresPermissions("sys:session:list")
    @GetMapping("sys/session/list")
    public String list() {
        return "sys/session/list";
    }

    /**
     * 在线用户管理
     */
    @RequiresPermissions("sys:session:list")
    @PostMapping("sys/session/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        return sessionService.selectPageList(para);
    }

    /**
     * 强制下线
     *
     * @param sessionId 会话id
     */
    @KrtLog("强制下线")
    @RequiresPermissions("sys:session:deleteUser")
    @PostMapping("sys/session/deleteUser")
    @ResponseBody
    public ReturnBean deleteUser(String sessionId) {
        sessionService.deleteUser(sessionId);
        return ReturnBean.ok();
    }

    /**
     * 批量下线
     *
     * @param sessionIds 会话id
     */
    @KrtLog("批量下线")
    @RequiresPermissions("sys:session:deleteUsers")
    @PostMapping("sys/session/deleteUsers")
    @ResponseBody
    public ReturnBean deleteUsers(String sessionIds) {
        sessionService.deleteUsers(sessionIds);
        return ReturnBean.ok();
    }
}
