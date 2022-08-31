package com.krt.api.controller;

import com.krt.common.annotation.KrtIgnoreAuth;
import com.krt.common.bean.ReturnBean;
import com.krt.complain.entity.Complain;
import com.krt.complain.service.IComplainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("api/complain")
@Api(description = "投诉举报接口API")
public class ApiComplainController {

    @Autowired
    private IComplainService complainService;

    @KrtIgnoreAuth
    @PostMapping("complainSave")
    @ApiOperation(value = "投诉举报",notes = "投诉举报")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType="complain", name = "complain", value = "投诉举报实体", required = true)
    })
    public ReturnBean complainSave(Complain complain) {
        complain.setCpnTime(new Date());
        complainService.insertAndMsgLog(complain);
        return ReturnBean.ok();
    }
}
