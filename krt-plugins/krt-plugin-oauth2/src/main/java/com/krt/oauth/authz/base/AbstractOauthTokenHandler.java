package com.krt.oauth.authz.base;

import com.alibaba.fastjson.JSON;
import com.krt.common.bean.ReturnBean;
import com.krt.common.bean.ReturnCode;
import com.krt.common.util.ServletUtils;
import com.krt.oauth.authz.OauthTokenxRequest;
import com.krt.oauth.authz.validator.AbstractOauthTokenValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import javax.servlet.http.HttpServletResponse;

/**
 * Token处理器抽象类
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月09日
 */
@Slf4j
public abstract class AbstractOauthTokenHandler extends AbstractOauthHandler implements OauthTokenHandler {

    /**
     * 验证器
     *
     * @return AbstractOauthTokenValidator
     */
    protected abstract AbstractOauthTokenValidator getValidator();

    /**
     * token请求request
     */
    protected OauthTokenxRequest tokenRequest;

    /**
     * 服务器响应response
     */
    protected HttpServletResponse response;

    /**
     * 处理器
     *
     * @param tokenRequest
     * @param response
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     */
    @Override
    public final void handle(OauthTokenxRequest tokenRequest, HttpServletResponse response) throws OAuthProblemException, OAuthSystemException {
        this.tokenRequest = tokenRequest;
        this.response = response;
        //1、开始验证规则
        if (validateFailed()) {
            return;
        }
        //2、验证通过后处理
        handleAfterValidation();
    }

    /**
     * 不同模式的规则验证
     *
     * @throws OAuthSystemException
     */
    protected boolean validateFailed() throws OAuthSystemException {
        AbstractOauthTokenValidator validator = getValidator();
        log.debug("Use [{}] validate client: {}", validator, tokenRequest.getClientId());
        final ReturnBean returnBean = validator.validate();
        return checkAndResponseValidateFailed(returnBean);
    }

    /**
     * 规则检测失败返回
     *
     * @param returnBean
     */
    protected boolean checkAndResponseValidateFailed(ReturnBean returnBean ) {
        if (returnBean != null && returnBean.getCode() != ReturnCode.OK.getCode()) {
            log.debug("Validate OAuthAuthzRequest(client_id={}) failed ,returnBean={}", tokenRequest.getClientId(),returnBean);
            ServletUtils.printJson(response, JSON.toJSON(returnBean));
            return true;
        }
        return false;
    }


    /**
     * 验证通过后 规则处理
     *
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     */
    protected abstract void handleAfterValidation() throws OAuthProblemException, OAuthSystemException;

    /**
     * 获取客户端id
     */
    @Override
    protected String clientId() {
        return tokenRequest.getClientId();
    }


}
