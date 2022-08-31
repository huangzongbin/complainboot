package com.krt.sys.service.impl;

import com.krt.common.bean.DataTable;
import com.krt.common.constant.GlobalConstant;
import com.krt.common.util.ShiroUtils;
import com.krt.sys.entity.User;
import com.krt.sys.entity.UserOnline;
import com.krt.sys.service.ISessionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * session管理服务实现层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年10月25日
 */
@Slf4j
@Service
public class SessionServiceImpl implements ISessionService {

    @Autowired
    private SessionDAO sessionDAO;

    /**
     * 获取在线session信息
     *
     * @param para 分页参数
     */
    @Override
    public DataTable selectPageList(Map para) {
        List<UserOnline> list = new ArrayList<>();
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        Session currentSession = ShiroUtils.getSession();
        //分页
        Integer length = 10;
        Integer start = 0;
        //获取全部分页数据
        //总数
        Long count = 0L;
        Integer i = 0;
        Integer j = 0;
        for (Session session : sessions) {
            UserOnline userOnline = new UserOnline();
            SimplePrincipalCollection principalCollection;
            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
                log.debug("PRINCIPALS_SESSION_KEY----------空");
                continue;
            } else {
                principalCollection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            }
            User user = (User) principalCollection.getPrimaryPrincipal();
            userOnline.setUsername(user.getUsername());
            userOnline.setId((String) session.getId());
            userOnline.setHost(session.getHost());
            userOnline.setStartTimestamp(session.getStartTimestamp());
            userOnline.setLastAccessTime(session.getLastAccessTime());
            userOnline.setTimeout(session.getTimeout());
            if (currentSession.getId().equals(session.getId())) {
                userOnline.setSelf(true);
            } else {
                userOnline.setSelf(false);
            }
            if (i < length && j >= start) {
                list.add(userOnline);
                i++;
            }
            j++;
            count++;
        }
        DataTable dataTable = new DataTable();
        dataTable.setPageNum(Long.valueOf(start));
        dataTable.setData(list);
        dataTable.setRecordsFiltered(count);
        return dataTable;
    }

    /**
     * 获取session
     *
     * @param sessionId
     */
    @Override
    public Session getSessionById(String sessionId) {
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            if (sessionId.equals(session.getId())) {
                return session;
            }
        }
        return null;
    }

    /**
     * 踢出用户
     *
     * @param sessionId
     */
    @Override
    public void deleteUser(String sessionId) {
        Session session = getSessionById(sessionId);
        if (session != null) {
            sessionDAO.delete(session);
        }
    }

    /**
     * 批量踢出用户
     *
     * @param sessionIds
     */
    @Override
    public void deleteUsers(String sessionIds) {
        String[] sessionIdArr = sessionIds.split(GlobalConstant.COMMA);
        for (String sessionId : sessionIdArr) {
            deleteUser(sessionId);
        }
    }
}
