package com.krt.jpush.service;

import com.krt.jpush.dto.PushDTO;

/**
 * 推送服务层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月03日
 */
public interface IPushService {

    /**
     * 群发Alert消息
     *
     * @param pushDTO 推送dto
     * @return true 成功 false 失败
     */
    boolean pushAlertToAll(PushDTO pushDTO);

    /**
     * 群发Message消息
     *
     * @param pushDTO 推送dto
     * @return true 成功 false 失败
     */
    boolean pushMessageToAll(PushDTO pushDTO);

    /**
     * 单发Alert消息
     *
     * @param pushDTO 推送dto
     * @return true 成功 false 失败
     */
    boolean pushAlertToOne(PushDTO pushDTO);

    /**
     * 单发Message
     *
     * @param pushDTO 推送dto
     * @return true 成功 false 失败
     */
    boolean pushMessageToOne(PushDTO pushDTO);
}
