package com.krt.file.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.krt.common.annotation.KrtLog;
import com.krt.common.bean.DataTable;
import com.krt.common.bean.Query;
import com.krt.common.bean.ReturnBean;
import com.krt.file.entity.FileResult;
import com.krt.file.service.IFileResultService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 文件控制层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年09月11日
 */
@Controller
public class FileResultController extends LinkedHashMap {

    @Autowired
    private IFileResultService fileResultService;

    /**
     * 文件管理页
     */
    @RequiresPermissions("file:fileResult:list")
    @GetMapping("file/fileResult/list")
    public String list() {
        return "file/fileResult/list";
    }

    /**
     * 文件管理
     *
     * @param para 搜索参数
     */
    @RequiresPermissions("file:fileResult:list")
    @PostMapping("file/fileResult/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        Query query = new Query(para);
        LambdaQueryWrapper<FileResult> queryWrapper = new LambdaQueryWrapper();
        IPage page = fileResultService.selectPage(query.getPage(), queryWrapper);
        return DataTable.ok(page);
    }

    /**
     * 删除文件
     *
     * @param id 文件id
     */
    @KrtLog("删除文件")
    @RequiresPermissions("file:fileResult:delete")
    @PostMapping("file/fileResult/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        fileResultService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除文件
     *
     * @param ids 文件ids
     */
    @KrtLog("批量删除文件")
    @RequiresPermissions("file:fileResult:delete")
    @PostMapping("file/fileResult/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        fileResultService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }

    /**
     * 检测文件MD5
     *
     * @param md5 文件md5
     */
    @PostMapping("file/fileResult/checkMd5")
    @ResponseBody
    public ReturnBean checkMd5(String md5) {
        FileResult fileResult = fileResultService.selectByMd5(md5);
        if (fileResult != null) {
            return ReturnBean.ok(fileResult.getMd5());
        } else {
            return ReturnBean.error();
        }
    }

}
