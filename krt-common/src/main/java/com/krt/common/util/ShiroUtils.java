package com.krt.common.util;

import com.krt.common.constant.GlobalConstant;
import com.krt.common.datascope.DataScope;
import com.krt.common.session.SessionUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * Shiro工具类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年7月20日
 */
@Slf4j
public class ShiroUtils {

    /**
     * 加密算法
     */
    public final static String HASH_ALGORITHM_NAME = "MD5";

    /**
     * 循环次数
     */
    public final static int  HASH_ITERATIONS = 1024;

    /**
     * 获取Session对象
     *
     * @return Session
     */
    public static Session getSession() {
        Subject subject = getSubject();
        if (subject != null) {
            return subject.getSession();
        } else {
            return null;
        }
    }

    /**
     * 获取Subject对象
     *
     * @return Subject
     */
    public static Subject getSubject() {
        try {
            return SecurityUtils.getSubject();
        } catch (UnavailableSecurityManagerException e) {

        }
        return null;
    }


    /**
     * 获取session用户
     *
     * @return User
     */
    public static SessionUser getSessionUser() {
        try {
            Session session = getSession();
            if (session != null) {
                return (SessionUser) session.getAttribute(GlobalConstant.SESSION_USER);
            } else {
                return null;
            }
        } catch (UnknownSessionException e) {

        }
        return null;
    }

    /**
     * 保存session属性
     *
     * @param key   键
     * @param value 值
     */
    public static void setSessionAttribute(String key, Object value) {
        log.debug("保存session属性:key:{}  value:{}", key, value);
        Session session = getSession();
        if (session != null) {
            session.setAttribute(key, value);
        }
    }

    /**
     * 获取session属性
     *
     * @param key 键
     * @return Object 值
     */
    public static Object getSessionAttribute(Object key) {
        Session session = getSession();
        if (session != null) {
            return session.getAttribute(key);
        } else {
            return null;
        }
    }


    /**
     * 获取session验证码
     *
     * @param key 验证码
     */
    public static String getKaptcha(String key) {
        String kaptcha = (String) getSessionAttribute(key);
        getSession().removeAttribute(key);
        return kaptcha;
    }

    /**
     * 判断是否拥有某个角色
     *
     * @param roleCode 角色编码
     */
    public boolean hasRole(String roleCode) {
        Subject subject = getSubject();
        if (subject != null) {
            return subject.hasRole(roleCode);
        } else {
            return false;
        }
    }


    /**
     * 获取加密数据
     *
     * @param password
     * @param salt
     */
    public static String getMD5(String password, String salt) {
        return new SimpleHash(HASH_ALGORITHM_NAME, password, salt, HASH_ITERATIONS).toHex();
    }

    /**
     * 获取数据权限
     */
    public static DataScope getDataScope() {
        DataScope dataScope = new DataScope();
        List<Object> organIds = (List<Object>) getSessionAttribute(GlobalConstant.DATA_SCOPE);
        dataScope.setScopeValue(organIds);
        return dataScope;
    }

    public static void main(String[] args) {

    }
}
