package com.krt.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.krt.api.dto.WeChatHouseholdDto;
import com.krt.api.enums.DataStatusEnum;
import com.krt.common.annotation.KrtIgnoreAuth;
import com.krt.common.base.BaseController;
import com.krt.common.bean.ReturnBean;
import com.krt.common.validator.Assert;
import com.krt.pay.service.IPayOrderService;
import com.krt.rent.entity.RentHouse;
import com.krt.rent.service.IRentHouseService;
import com.krt.sys.entity.WeChatHousehold;
import com.krt.sys.service.IWeChatHouseholdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * 绑定功能已经不再需要
 * @author zhangdb
 * @date 2019/6/10 9:09
 */
@Slf4j
@RestController
@RequestMapping("api")
@Api(tags = "微信账号与住户绑定")
public class ApiBoundController extends BaseController {

    @Autowired
    private IWeChatHouseholdService weChatHouseholdService;

    @Autowired
    private IRentHouseService rentHouseService;

    @Autowired
    private IPayOrderService payOrderService;

    /**
     * 通过微信标识判断用户是否绑定租户信息
     * @param uniqueId
     * @return
     * @throws Exception
     */
/*    @KrtIgnoreAuth
    @PostMapping("isBound")
    @ApiOperation(value = "查询用户微信账号是否已经绑定租户信息", notes = "返回 data = 0 表示未绑定，进入绑定界面，否则返回绑定租户身份证号")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "uniqueId", value = "微信在公众号唯一标识", required = true)
    })
    public ReturnBean isBound(String uniqueId) throws Exception {
        Assert.isBlank(uniqueId,"请输入微信在公众号唯一标识");
        String weChatUuid = payOrderService.getWeChatUuid(uniqueId);
        // 判断用户是否绑定租户信息
        LambdaQueryWrapper<WeChatHousehold> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WeChatHousehold::getWechatUuid,weChatUuid)
                    .eq(WeChatHousehold::getStatus,DataStatusEnum.VALID.getValue());
        Map<String, Object> weChatHousehold = weChatHouseholdService.selectMap(queryWrapper);
        Object idCard = weChatHousehold == null ? 0 : weChatHousehold.get("idCard");
        return ReturnBean.ok(idCard);
    }*/

    /**
     * 解除/解除绑定
     * @param wechatHouseholdDto
     * @return
     * @throws Exception
     */
    /*@KrtIgnoreAuth
    @PostMapping("unbound")
    @ApiOperation(value = "解除/解除绑定",notes = "由status区分绑定或解除绑定,code == 100 表示租户信息不正确，弹出提示 + </br>" +
            "code == 101 表示微信号已绑定租户信息，弹出提示")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "uniqueId", value = "微信在公众号唯一标识", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "name", value = "姓名,绑定时必填", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "idCard", value = "身份证号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "status", value = "1 代表绑定；2代表解除绑定", required = true),
    })
    public ReturnBean unbound(WeChatHouseholdDto wechatHouseholdDto) throws Exception {
        // 校验
        String weChatUuid = payOrderService.getWeChatUuid(wechatHouseholdDto.getUniqueId());
        Assert.isBlank(wechatHouseholdDto.getUniqueId(),"微信在公众号唯一标识不能为空");
        Assert.isBlank(wechatHouseholdDto.getIdCard(),"身份证号不能为空");

        WeChatHousehold weChatHousehold = new WeChatHousehold();
        BeanUtils.copyProperties(wechatHouseholdDto,weChatHousehold);
        weChatHousehold.setWechatUuid(weChatUuid);
        if (DataStatusEnum.VALID.getValue().equals(wechatHouseholdDto.getStatus())) {
            // 绑定
            Assert.isBlank(wechatHouseholdDto.getName(),"姓名不能为空");
            // 判断是否存在该住户信息
            LambdaQueryWrapper<RentHouse> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(RentHouse::getIdCard,wechatHouseholdDto.getIdCard())
                        .eq(RentHouse::getName,wechatHouseholdDto.getName());
            RentHouse rentHouse = rentHouseService.selectOne(queryWrapper);
            if (rentHouse == null) {
                return ReturnBean.error(100,"用户信息不存在，请检查姓名及身份证号是否输入正确");
            }
            // 判断该微信号是否已经绑定了住户并且状态为有效
            LambdaQueryWrapper<WeChatHousehold> weChatHouseholdWrapper = new LambdaQueryWrapper<>();
            weChatHouseholdWrapper.eq(WeChatHousehold::getWechatUuid,weChatUuid)
                                  .eq(WeChatHousehold::getStatus,DataStatusEnum.VALID.getValue());
            int count = weChatHouseholdService.selectCount(weChatHouseholdWrapper);
            if (count > 0) {
                return ReturnBean.error(101,"微信号已绑定租户信息，请先解绑");
            }
            weChatHouseholdService.insert(weChatHousehold);
        } else {
            // 解绑
            weChatHouseholdService.unbound(weChatHousehold);
        }
        return ReturnBean.ok();
    }*/

}
