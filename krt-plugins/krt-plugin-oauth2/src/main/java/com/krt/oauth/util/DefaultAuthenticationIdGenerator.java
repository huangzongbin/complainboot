package com.krt.oauth.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


/**
 * AuthenticationId 的生成器
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月09日
 */
@Slf4j
@Component
public class DefaultAuthenticationIdGenerator {

    public DefaultAuthenticationIdGenerator() {
    }

    /**
     * 生成规则
     *
     * @param clientId
     * @param username
     * @param scope
     */
    public String generate(String clientId, String username, String scope) {
        Map<String, String> map = new HashMap(3);
        map.put(OAuth.OAUTH_CLIENT_ID, clientId);
        //check it is client only
        if (!clientId.equals(username)) {
            map.put(OAuth.OAUTH_USERNAME, username);
        }
        if (!OAuthUtils.isEmpty(scope)) {
            map.put(OAuth.OAUTH_SCOPE, scope);
        }
        return digest(map);
    }

    /**
     * 加密
     *
     * @param map
     */
    protected String digest(Map<String, String> map) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.warn("Digest error", e);
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
        }

        try {
            byte[] bytes = digest.digest(map.toString().getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        } catch (UnsupportedEncodingException e) {
            log.warn("Encoding error", e);
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
        }
    }

}
