package com.krt.api.controller.v1;

import com.alibaba.fastjson.JSONObject;
import com.krt.common.annotation.KrtApiVersion;
import com.krt.common.annotation.KrtLoginUser;
import com.krt.common.bean.ReturnBean;
import com.krt.common.validator.Assert;
import com.krt.common.validator.ValidatorUtils;
import com.krt.jpush.dto.PushDTO;
import com.krt.jpush.enums.AppTypeEnum;
import com.krt.jpush.enums.AudienceEnum;
import com.krt.jpush.enums.PushTypeEnum;
import com.krt.jpush.service.IPushService;
import com.krt.sys.entity.User;
import com.krt.sys.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 推送接口
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月03日
 */
@Slf4j
@RestController
@KrtApiVersion(1)
@RequestMapping("api/{version}/")
@Api(tags = "2、推送接口API")
public class ApiV1PushController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IPushService pushService;

    /**
     * 绑定推送token
     *
     * @param registrationId 推送token
     * @param appType        app类型
     * @param user           用户
     */
    @PostMapping("bindPushToken")
    @ApiOperation(value = "绑定推送token")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "version", value = "version", required = true),
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "accessToken"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "registrationId", value = "推送token", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "appType", value = "APP类型：android || ios", required = true),
    })
    public ReturnBean bindToken(String registrationId, String appType, @ApiIgnore @KrtLoginUser User user) {
        log.debug("============绑定推送token开始============");
        //1、验证实体
        Assert.isEmpty(registrationId, "推送token不可为空！");
        Assert.isNull(user, "accessToken无效");
        if (!AppTypeEnum.ANDROID.getValue().equalsIgnoreCase(appType) && !AppTypeEnum.IOS.getValue().equalsIgnoreCase(appType)) {
            return ReturnBean.paramError( "APP类型错误");
        }
        user.setAppType(appType);
        user.setRegistrationId(registrationId);
        user.setPassword(null);
        userService.updateById(user);
        log.debug("============绑定推送token结束============");
        return ReturnBean.ok();
    }


    /**
     * 注销推送token
     *
     * @param user 用户
     */
    @PostMapping("unbindPushToken")
    @ApiOperation(value = "注销推送token")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "version", value = "version", required = true),
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "accessToken")
    })
    public ReturnBean<Integer> unbindPushToken(@ApiIgnore @KrtLoginUser User user) {
        log.debug("============注销推送token开始============");
        //1、验证实体
        Assert.isNull(user, "accessToken无效");
        user.setRegistrationId("0");
        user.setPassword(null);
        userService.updateById(user);
        log.debug("============注销推送token结束============");
        return ReturnBean.ok();
    }

    /**
     * 推送消息
     *
     * @param pushDTO 推送dto
     */
    @PostMapping("push")
    @ApiOperation(value = "推送消息", notes = "手机推送")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "version", value = "version", required = true),
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "accessToken")
    })
    public ReturnBean push(@RequestBody PushDTO pushDTO) {
        log.debug("============推送开始============");
        log.debug("pushDTO:{}", pushDTO);
        //1、验证实体
        ValidatorUtils.validateEntity(pushDTO);
        //判断自定义参数
        String param = pushDTO.getParam();
        Map<String, Object> extras;
        if (!Assert.isEmpty(param)) {
            extras = new HashMap<>(16);
            try {
                JSONObject paraJson = JSONObject.parseObject(param);
                log.debug("paraJson:" + paraJson);
                for (Map.Entry<String, Object> entry : paraJson.entrySet()) {
                    extras.put(entry.getKey(), entry.getValue());
                }
                pushDTO.setExtras(extras);
            } catch (Exception e) {
                return ReturnBean.paramError( "param格式错误,请输入json字符串");
            }
        }
        //群发
        if (AudienceEnum.ALL.getValue().equalsIgnoreCase(pushDTO.getAudience())) {
            String pushType = pushDTO.getPushType();
            if (PushTypeEnum.ALERT.getValue().equalsIgnoreCase(pushType)) {
                if (pushService.pushAlertToAll(pushDTO)) {
                    return ReturnBean.ok();
                } else {
                    return ReturnBean.error();
                }
            }
            if (PushTypeEnum.MESSAGE.getValue().equalsIgnoreCase(pushType)) {
                if (pushService.pushMessageToAll(pushDTO)) {
                    return ReturnBean.ok();
                } else {
                    return ReturnBean.error();
                }
            }
            return ReturnBean.paramError( "推送消息类型错误");

        }
        //单个推送
        if (AudienceEnum.ONE.getValue().equalsIgnoreCase(pushDTO.getAudience())) {
            String pushType = pushDTO.getPushType();
            if (PushTypeEnum.ALERT.getValue().equalsIgnoreCase(pushType)) {
                if (pushService.pushAlertToOne(pushDTO)) {
                    return ReturnBean.ok();
                } else {
                    return ReturnBean.error();
                }
            }
            if (PushTypeEnum.MESSAGE.getValue().equalsIgnoreCase(pushType)) {
                if (pushService.pushMessageToOne(pushDTO)) {
                    return ReturnBean.ok();
                } else {
                    return ReturnBean.error();
                }

            }
            return ReturnBean.paramError( "推送消息类型错误");
        }
        return ReturnBean.paramError( "推送对象类型错误");
    }
}
