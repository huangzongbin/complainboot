package com.krt.sys.service;

import com.krt.common.bean.DataTable;
import org.apache.shiro.session.Session;

import java.util.Map;

/**
 * session管理服务接口层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年10月25日
 */
public interface ISessionService {

    /**
     * 获取在线session信息
     *
     * @param para 分页参数
     * @return 在线信息
     */
    DataTable selectPageList(Map para);

    /**
     * 获取session
     *
     * @param sessionId
     * @return session
     */
    Session getSessionById(String sessionId);

    /**
     * 踢出用户
     *
     * @param sessionId
     */
    void deleteUser(String sessionId);

    /**
     * 批量踢出用户
     *
     * @param sessionIds
     */
    void deleteUsers(String sessionIds);
}
