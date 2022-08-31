package com.krt.jpush.util;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 推送工具类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年08月18日
 */
@Slf4j
public class PushUtils {


    /**
     * 构建android 单个推送alert
     *
     * @param registrationId 注册id
     * @param alert          推送内容
     * @param extras         自定义参数
     */
    public static PushPayload buildPushObjectAndroidOneAlert(String registrationId, String alert, Integer alertType, Map extras) {
        if (alertType == null) {
            alertType = -1;
        }
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(registrationId))
                .setNotification(
                        Notification.newBuilder()
                                .setAlert(alert)
                                .addPlatformNotification(
                                        AndroidNotification.newBuilder()
                                                .setAlertType(alertType)
                                                .addExtras(extras)
                                                .build()
                                ).build()
                )
                .build();
    }

    /**
     * 构建ios 单个推送alert
     *
     * @param registrationId 注册id
     * @param alert          推送内容
     * @param extras         自定义参数
     * @param apns           APNs 的推送环境是“生产” true（如果不显式设置的话，Library 会默认指定为开发 false）
     */
    public static PushPayload buildPushObjectIosOneAlert(String registrationId, String alert, Map extras, Boolean apns) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(registrationId))
                .setNotification(Notification.ios(alert, extras))
                .setOptions(Options.newBuilder()
                        .setApnsProduction(apns)
                        .build())
                .build();
    }

    /**
     * 构建群发推送alert
     *
     * @param alert  推送内容
     * @param extras 自定义参数
     * @param apns   APNs 的推送环境是“生产” true（如果不显式设置的话，Library 会默认指定为开发 false）
     */
    public static PushPayload buildPushObjectAllAllAlert(String alert, Map extras, Boolean apns) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .setAlert(alert)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtras(extras).build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .addExtras(extras).build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(apns)
                        .build())
                .build();
    }


    /**
     * 构建android 单个推送message
     *
     * @param registrationId 注册id
     * @param content        推送内容
     */
    public static PushPayload buildPushObjectAndroidOneMessage(String registrationId, String content) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(registrationId))
                .setMessage(Message.content(content))
                .build();
    }

    /**
     * ios 单个推送message
     *
     * @param registrationId 注册id
     * @param content        推送内容
     * @param apns           APNs 的推送环境是“生产” true（如果不显式设置的话，Library 会默认指定为开发 false）
     */
    public static PushPayload buildPushObjectIosOneMessage(String registrationId, String content, Boolean apns) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(registrationId))
                .setMessage(Message.content(content))
                .setOptions(Options.newBuilder()
                        .setApnsProduction(apns)
                        .build())
                .build();
    }

    /**
     * 群发自定义message
     *
     * @param content 推送内容
     * @param apns    APNs 的推送环境是“生产” true（如果不显式设置的话，Library 会默认指定为开发 false）
     */
    public static PushPayload buildPushObjectAllAllMessage(String content, Boolean apns) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setMessage(Message.content(content))
                .setOptions(Options.newBuilder()
                        .setApnsProduction(apns)
                        .build())
                .build();
    }


    /**
     * 推送
     *
     * @param jpushClient 推送客户端
     * @param payload     参数
     */
    public static boolean push(JPushClient jpushClient, PushPayload payload) {
        try {
            //4、访问推送api
            PushResult result = jpushClient.sendPush(payload);
            log.debug("推送接口返回：{}", result);
            return result.isResultOK();
        } catch (APIConnectionException e) {
            log.error("连接失败", e);
            return false;
        } catch (APIRequestException e) {
            log.error(" 请求错误", e);
            log.info("HTTP Status: {}", e.getStatus());
            log.info("Error Code: {}" + e.getErrorCode());
            log.info("Error Message: {}" + e.getErrorMessage());
            return false;
        }
    }


}
