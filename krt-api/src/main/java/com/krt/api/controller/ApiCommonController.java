package com.krt.api.controller;

import com.krt.common.annotation.KrtIgnoreAuth;
import com.krt.common.bean.ReturnBean;
import com.krt.sys.service.IDicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zhangdb
 * @Description: 通用接口：如根据类型获取字典
 * @date 2018/9/18 11:01
 */

@RestController
@RequestMapping("api")
@Api(description = "通用接口")
public class ApiCommonController {

    @Autowired
    private IDicService dicService;

    @KrtIgnoreAuth
    @PostMapping("getDictionaries")
    @ApiOperation(value = "获取字典", notes = "多个字典用逗号隔开")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "types", value = "字典类型，多个用逗号隔开", required = true),
    })
    public ReturnBean getDictionaries(String types) {
        Map resultMap = dicService.getDictionaries(types);
        return ReturnBean.ok(resultMap);
    }

}
