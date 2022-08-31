package com.krt.redis.dao;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

import java.io.Serializable;
import java.util.Collection;

/**
 * redis 接管 shiro Session
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年07月08日
 */
@Slf4j
public class RedisSessionDAO extends EnterpriseCacheSessionDAO {

    @Override
    protected void doUpdate(Session session) {
        if (session == null || session.getId() == null) {
            return;
        }
        createActiveSessionsCache().put(session.getId(), session);
    }

    @Override
    protected void doDelete(Session session) {
        if (session == null || session.getId() == null) {
            return;
        }
        createActiveSessionsCache().remove(session.getId());

    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        if (session == null || session.getId() == null) {
            log.debug("session不存在");
        } else {
            //保存session
            createActiveSessionsCache().put(sessionId, session);
        }
        return sessionId;
    }

    @Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException {
        return super.readSession(sessionId);
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return super.doReadSession(sessionId);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return createActiveSessionsCache().values();
    }
}