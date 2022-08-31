package com.krt.quartz.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.DataTable;
import com.krt.common.bean.Query;
import com.krt.common.bean.ReturnBean;
import com.krt.common.util.SpringUtils;
import com.krt.common.validator.Assert;
import com.krt.quartz.entity.Quartz;
import com.krt.quartz.service.IQuartzService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.CronScheduleBuilder;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/**
 * 任务调度控制层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年7月22日
 */
@Slf4j
@Controller
public class QuartzController extends BaseController {

    @Autowired
    private IQuartzService quartzService;

    /**
     * 表达式设置
     *
     * @return {@link String}
     */
    @GetMapping("quartz/quartzCron")
    public String quartzCron() {
        return "quartz/quartzCron";
    }

    /**
     * 任务调度管理
     *
     * @return {@link String}
     */
    @RequiresPermissions("quartz:list")
    @GetMapping("quartz/list")
    public String list() {
        return "quartz/list";
    }

    /**
     * 任务调度管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("quartz:list")
    @PostMapping("quartz/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        Query query = new Query(para);
        LambdaQueryWrapper<Quartz> queryWrapper = new LambdaQueryWrapper<>();
        IPage page = quartzService.selectPage(query.getPage(), queryWrapper);
        return DataTable.ok(page);
    }

    /**
     * 添加任务
     *
     * @return {@link String}
     */
    @RequiresPermissions("quartz:insert")
    @GetMapping("quartz/insert")
    public String insert() {
        return "quartz/insert";
    }

    /**
     * 添加任务
     *
     * @param quartz 定时任务
     * @return {@link ReturnBean}
     */
    @KrtLog("添加任务")
    @RequiresPermissions("quartz:insert")
    @PostMapping("quartz/insert")
    @ResponseBody
    public ReturnBean insert(Quartz quartz) {
        try {
            CronScheduleBuilder.cronSchedule(quartz.getExpression());
        } catch (Exception e) {
            return ReturnBean.error("cron表达式有误，不能被解析！");
        }
        ReturnBean rb = checkBean(quartz);
        if (rb == null) {
            quartzService.insert(quartz);
            return ReturnBean.ok();
        } else {
            return rb;
        }
    }


    /**
     * 启动 or 停止任务
     *
     * @param id     任务id
     * @param status 任务状态
     * @return {@link ReturnBean}
     */
    @KrtLog("启动or停止任务")
    @PostMapping("quartz/startOrStop")
    @ResponseBody
    public ReturnBean startOrStop(Integer id, String status) throws SchedulerException {
        Quartz quartz = quartzService.selectById(id);
        ReturnBean rb = checkBean(quartz);
        if (rb == null) {
            quartzService.updateStatus(id, status);
            return ReturnBean.ok();
        } else {
            return rb;
        }

    }

    /**
     * 修改任务页
     *
     * @param id 任务id
     * @return {@link String}
     */
    @RequiresPermissions("quartz:update")
    @GetMapping("quartz/update")
    public String update(Integer id) {
        Quartz quartz = quartzService.selectById(id);
        request.setAttribute("quartz", quartz);
        return "quartz/update";
    }

    /**
     * 修改任务
     *
     * @param quartz 定时任务
     * @return {@link ReturnBean}
     */
    @KrtLog("修改任务")
    @RequiresPermissions("quartz:update")
    @PostMapping("quartz/update")
    @ResponseBody
    public ReturnBean update(Quartz quartz) {
        try {
            CronScheduleBuilder.cronSchedule(quartz.getExpression());
        } catch (Exception e) {
            return ReturnBean.error("cron表达式有误，不能被解析！");
        }
        ReturnBean rb = checkBean(quartz);
        if (rb == null) {
            quartzService.updateById(quartz);
            return ReturnBean.ok();
        } else {
            return rb;
        }

    }

    /**
     * 删除任务
     *
     * @param id 任务id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除任务")
    @RequiresPermissions("quartz:delete")
    @PostMapping("quartz/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        quartzService.deleteById(id);
        return ReturnBean.ok();
    }


    /**
     * 批量删除任务
     *
     * @param ids 任务ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除任务")
    @RequiresPermissions("quartz:delete")
    @PostMapping("quartz/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        quartzService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }

    /**
     * 立即执行一次任务
     *
     * @param id 任务id
     * @return {@link ReturnBean}
     */
    @KrtLog("立即执行一次任务")
    @RequiresPermissions("quartz:doOnce")
    @PostMapping("quartz/doOnce")
    @ResponseBody
    public ReturnBean doOnce(Integer id) throws SchedulerException {
        Quartz quartz = quartzService.selectById(id);
        ReturnBean rb = checkBean(quartz);
        if (rb == null) {
            quartzService.doOnce(id);
            return ReturnBean.ok();
        } else {
            return rb;
        }
    }

    /**
     * 检测任务名和组名
     *
     * @param id    任务id
     * @param name  任务名
     * @param groupName 组名
     * @return {@link Boolean}
     */
    @PostMapping("quartz/checkName")
    @ResponseBody
    public Boolean checkName(Integer id, String name, String groupName) {
        LambdaQueryWrapper<Quartz> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Quartz::getName, name);
        queryWrapper.eq(Quartz::getGroupName, groupName);
        queryWrapper.ne(!Assert.isNull(id), Quartz::getId, id);
        Integer count = quartzService.selectCount(queryWrapper);
        return count == 0;
    }

    /*****************************私有方法*************************************/

    /**
     * 检测bean
     *
     * @param quartz 定时器
     * @return {@link ReturnBean}
     */
    private ReturnBean checkBean(Quartz quartz) {
        Object obj;
        try {
            if (quartz.getSpringId() != null && !"".equals(quartz.getSpringId())) {
                obj = SpringUtils.getBean(quartz.getSpringId());
            } else {
                Class clazz = Class.forName(quartz.getBeanClass());
                obj = clazz.newInstance();
            }
        } catch (Exception e) {
            return ReturnBean.error("执行任务类不存在");
        }
        if (obj == null) {
            return ReturnBean.error("执行任务类不存在");
        } else {
            Class clazz = obj.getClass();
            Method method;
            try {
                method = clazz.getMethod(quartz.getMethod());
            } catch (Exception e) {
                return ReturnBean.error("未找到目标方法");
            }
            if (method == null) {
                return ReturnBean.error("未找到目标方法");
            }
        }
        return null;
    }
}
