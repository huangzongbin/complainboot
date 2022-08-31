package com.krt.framework.controller;

import com.google.common.base.Joiner;
import com.krt.common.base.BaseController;
import com.krt.common.bean.ReturnBean;
import com.krt.common.validator.Assert;
import com.krt.sys.entity.Dic;
import com.krt.sys.service.IDicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import static com.krt.common.constant.GlobalConstant.COMMA;


/**
 * 公共控制层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月12日
 */
@Slf4j
@Controller
public class CommonController extends BaseController {

    @Autowired
    private IDicService dicService;

    /**
     * 公共的树页面
     *
     * @param url        json请求url
     * @param checkbox   是否开启多选 true , false
     * @param expandAll  是否展开 true , false
     * @param sId        选择属性 字段名称 默认id
     * @param sValue     选择值
     * @param chkboxType 多选父子关联 eg:ps-ps
     * @return {@link String}
     */
    @GetMapping("common/treeSelect")
    public String treeSelect(String url, String checkbox, String expandAll, String sId, String sValue, String chkboxType) throws UnsupportedEncodingException {
        Assert.isEmpty(url, "url参数不能为空");
        url = URLDecoder.decode(url, "utf-8");
        if (Assert.isEmpty(checkbox)) {
            checkbox = "false";
        }
        if (Assert.isEmpty(expandAll)) {
            expandAll = "false";
        }
        if (Assert.isEmpty(sId)) {
            sId = "id";
        }
        request.setAttribute("url", url);
        request.setAttribute("checkbox", checkbox);
        request.setAttribute("expandAll", expandAll);
        request.setAttribute("sId", sId);
        request.setAttribute("chkboxType", chkboxType);
        request.setAttribute("sValue", sValue);
        return "common/treeSelect";
    }

    /**
     * 获取字典
     *
     * @param type 字典类型
     * @param code 字典编码
     * @return {@link ReturnBean}
     */
    @ResponseBody
    @GetMapping("common/getDic")
    public ReturnBean getDic(String type, String code) {
        if (code.contains(COMMA)) {
            //code有逗号表示多个字典 如复选框
            String[] codeArr = code.split(COMMA);
            ArrayList<String> valList = new ArrayList<>();
            for (String c : codeArr) {
                Dic dic = dicService.selectByTypeAndCode(type, c);
                valList.add(dic.getName());
            }
            return ReturnBean.ok(Joiner.on(",").join(valList));
        } else {
            //单个字典
            Dic dic = dicService.selectByTypeAndCode(type, code);
            return ReturnBean.ok(dic != null ? dic.getName() : "");
        }

    }

    /**
     * 公共的列表选择页面
     *
     * @param url 页面请求url
     * @throws UnsupportedEncodingException 异常
     */
    @GetMapping("common/listSelect")
    public String listSelect(String url) throws UnsupportedEncodingException {
        Assert.isEmpty(url, "url参数不能为空");
        return URLDecoder.decode(url, "utf-8");
    }


}
