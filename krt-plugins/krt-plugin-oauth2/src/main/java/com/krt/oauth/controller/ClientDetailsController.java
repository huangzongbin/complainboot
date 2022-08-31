package com.krt.oauth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.krt.common.annotation.KrtLog;
import com.krt.common.base.BaseController;
import com.krt.common.bean.DataTable;
import com.krt.common.bean.Query;
import com.krt.common.bean.ReturnBean;
import com.krt.common.util.IdUtils;
import com.krt.common.validator.Assert;
import com.krt.oauth.entity.ClientDetails;
import com.krt.oauth.service.IClientDetailsService;
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
 * 客户端详情控制层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月20日
 */
@Controller
public class ClientDetailsController extends BaseController {

    @Autowired
    private IClientDetailsService clientDetailsService;

    /**
     * 客户端详情管理页
     */
    @RequiresPermissions("oauth:clientDetails:list")
    @GetMapping("oauth/clientDetails/list")
    public String list() {
        return "oauth/clientDetails/list";
    }

    /**
     * 客户端详情管理
     *
     * @param para 搜索参数
     */
    @RequiresPermissions("oauth:clientDetails:list")
    @PostMapping("oauth/clientDetails/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        Query query = new Query(para);
        LambdaQueryWrapper<ClientDetails> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!Assert.isEmpty(para.get("clientId")), ClientDetails::getClientId, para.get("clientId"));
        queryWrapper.like(!Assert.isEmpty(para.get("clientName")), ClientDetails::getClientName, para.get("clientName"));
        IPage<ClientDetails> page = clientDetailsService.selectPage(query.getPage(), queryWrapper);
        return DataTable.ok(page);
    }

    /**
     * 新增客户端详情页
     */
    @RequiresPermissions("oauth:clientDetails:insert")
    @GetMapping("oauth/clientDetails/insert")
    public String insert() {
        request.setAttribute("clientId", IdUtils.getUUID());
        request.setAttribute("clientSecret", IdUtils.getUUID());
        return "oauth/clientDetails/insert";
    }

    /**
     * 添加客户端详情
     *
     * @param clientDetails 客户端详情
     */
    @KrtLog("添加客户端详情")
    @RequiresPermissions("oauth:clientDetails:insert")
    @PostMapping("oauth/clientDetails/insert")
    @ResponseBody
    public ReturnBean insert(ClientDetails clientDetails) {
        clientDetailsService.insert(clientDetails);
        return ReturnBean.ok();
    }

    /**
     * 修改客户端详情页
     *
     * @param id 客户端详情 id
     */
    @RequiresPermissions("oauth:clientDetails:update")
    @GetMapping("oauth/clientDetails/update")
    public String update(Integer id) {
        ClientDetails clientDetails = clientDetailsService.selectById(id);
        request.setAttribute("clientDetails", clientDetails);
        return "oauth/clientDetails/update";
    }

    /**
     * 修改客户端详情
     *
     * @param clientDetails 客户端详情
     */
    @KrtLog("修改客户端详情")
    @RequiresPermissions("oauth:clientDetails:update")
    @PostMapping("oauth/clientDetails/update")
    @ResponseBody
    public ReturnBean update(ClientDetails clientDetails) {
        clientDetailsService.updateById(clientDetails);
        return ReturnBean.ok();
    }

    /**
     * 删除客户端详情
     *
     * @param id 客户端详情 id
     */
    @KrtLog("删除客户端详情")
    @RequiresPermissions("oauth:clientDetails:delete")
    @PostMapping("oauth/clientDetails/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        clientDetailsService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除客户端详情
     *
     * @param ids 客户端详情 ids
     */
    @KrtLog("批量删除客户端详情")
    @RequiresPermissions("oauth:clientDetails:delete")
    @PostMapping("oauth/clientDetails/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        clientDetailsService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }

    /**
     * 查看客户端详情页
     *
     * @param id 客户端详情 id
     */
    @RequiresPermissions("oauth:clientDetails:see")
    @GetMapping("oauth/clientDetails/see")
    public String see(Integer id) {
        ClientDetails clientDetails = clientDetailsService.selectById(id);
        request.setAttribute("clientDetails", clientDetails);
        return "oauth/clientDetails/see";
    }

    /**
     * 检测客户端id
     */
    @PostMapping("oauth/clientDetails/checkClientId")
    @ResponseBody
    public Boolean checkClientId(String clientId, Integer id) {
        LambdaQueryWrapper<ClientDetails> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ClientDetails::getClientId, clientId);
        queryWrapper.ne(!Assert.isNull(id), ClientDetails::getId, id);
        Integer count = clientDetailsService.selectCount(queryWrapper);
        return count == 0;
    }


    /**
     * 禁用||启用客户端
     */
    @KrtLog("禁用||启用客户端")
    @RequiresPermissions("oauth:clientDetails:state")
    @PostMapping("oauth/clientDetails/state")
    @ResponseBody
    public ReturnBean state(Integer id, Integer state) {
        ClientDetails details = clientDetailsService.selectById(id);
        ClientDetails newDetails =  new ClientDetails();
        newDetails.setId(id);
        newDetails.setClientId(details.getClientId());
        newDetails.setState(state);
        clientDetailsService.updateById(newDetails);
        return ReturnBean.ok();
    }

}
