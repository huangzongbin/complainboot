package com.krt.framework.shiro;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.apache.shiro.web.session.mgt.WebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import java.io.Serializable;

/**
 * 自定义WebSessionManager，用于替代DefaultWebSessionManager；
 * 解决：通过将session放入request 减少shiro的一次请求多次调用 doReadSession
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年04月18日
 */
public class KrtWebSessionManager extends DefaultWebSessionManager implements WebSessionManager {

    private static final Logger log = LoggerFactory.getLogger(DefaultWebSessionManager.class);

    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = getSessionId(sessionKey);
        if (sessionId == null) {
            log.debug("Unable to resolve session ID from SessionKey [{}].  Returning null to indicate a " + "session could not be found.", sessionKey);
            return null;
        }
        ServletRequest request = null;
        if (sessionKey instanceof WebSessionKey) {
            request = ((WebSessionKey) sessionKey).getServletRequest();
        }
        if (request != null) {
            Object s = request.getAttribute(sessionId.toString());
            if (s != null) {
                return (Session) s;
            }
        }
        Session s = retrieveSessionFromDataSource(sessionId);
        if (s == null) {
            String msg = "Could not find session with ID [" + sessionId + "]";
            throw new UnknownSessionException(msg);
        }
        if (request != null) {
            request.setAttribute(sessionId.toString(), s);
        }
        return s;
    }

}
