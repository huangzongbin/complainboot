package com.krt.sys.controller;

import com.krt.common.bean.ReturnBean;
import com.krt.sys.util.SysInfoUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 系统监控
 *
 * @author 殷帅
 * @version 1.0
 * @date 2019年04月11日
 */
@Controller
public class SysInfoController {

    /**
     * 系统监控页
     *
     * @return {@link String}
     */
    @GetMapping("/sys/sysInfo/index")
    public String index() {
        return "/sys/sysInfo/index";
    }


    /**
     * 获取服务器cpu内存信息
     *
     * @return {@link ReturnBean}
     */
    @GetMapping("/sys/sysInfo/cpuMemory")
    @ResponseBody
    public ReturnBean cpuMemory() {
        return ReturnBean.ok(SysInfoUtils.cpuMemory());
    }

    /**
     * 获取java堆栈信息
     *
     * @return {@link ReturnBean}
     */
    @GetMapping("/sys/sysInfo/jvmHeapDisk")
    @ResponseBody
    public ReturnBean jvmHeapDisk() {
        return ReturnBean.ok(SysInfoUtils.jvmHeapDisk());
    }
}
