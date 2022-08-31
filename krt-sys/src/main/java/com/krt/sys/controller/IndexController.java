package com.krt.sys.controller;

import com.krt.common.base.BaseController;
import com.krt.common.session.SessionUser;
import com.krt.common.util.ShiroUtils;
import com.krt.common.util.DateUtils;
import com.krt.sys.entity.Res;
import com.krt.sys.service.IResService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 后台首页控制层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年7月16日
 */
@Slf4j
@Controller
public class IndexController extends BaseController {

    @Autowired
    private IResService resService;

    @Value("${krt.version}")
    private String version;

    /**
     * 后台管理页
     */
    @GetMapping("index")
    public String index() {
        SessionUser sessionUser = ShiroUtils.getSessionUser();
        log.debug("当前登录用户:sessionUser:{}", sessionUser);
        List<Res> resList = resService.selectUserUrlRes(sessionUser);
        request.setAttribute("resList", resList);
        request.setAttribute("version", version);
        request.setAttribute("year", DateUtils.getYear());
        return "index";
    }

    /**
     * 欢迎页
     */
    @GetMapping("welcome")
    public String welcome() {
        return "welcome";
    }

}
