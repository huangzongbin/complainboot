package com.krt.oauth.authz.authorize;

import com.krt.oauth.authz.OauthxRequest;
import com.krt.oauth.authz.base.AbstractAuthorizeHandler;
import com.krt.oauth.authz.validator.AbstractClientDetailsValidator;
import com.krt.oauth.authz.validator.CodeClientDetailsValidator;
import com.krt.oauth.entity.ClientDetails;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


/**
 * response_type = 'code'请求控制器
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年12月09日
 */
@Slf4j
public class CodeAuthorizeHandler extends AbstractAuthorizeHandler {

    public CodeAuthorizeHandler(OauthxRequest oauthRequest, HttpServletResponse response) {
        super(oauthRequest, response);
    }

    @Override
    protected AbstractClientDetailsValidator getValidator() {
        return new CodeClientDetailsValidator(oauthRequest);
    }


    /**
     * 返回code
     *
     * @throws OAuthSystemException
     * @throws IOException
     */
    @Override
    protected void handleResponse() throws OAuthSystemException, IOException {
        final ClientDetails clientDetails = clientDetails();
        final String authCode = oauthService.insertAuthCode(clientDetails);
        final OAuthResponse oAuthResponse = OAuthASResponse
                .authorizationResponse(oauthRequest.request(), HttpServletResponse.SC_OK)
                .location(clientDetails.getRedirectUri())
                .setCode(authCode)
                .buildQueryMessage();
        log.debug(" 'code' response: {}", oAuthResponse);
        final String locationUri = oAuthResponse.getLocationUri();
        try {
            final Map<String, String> headers = oAuthResponse.getHeaders();
            for (String key : headers.keySet()) {
                response.addHeader(key, headers.get(key));
            }
            response.setStatus(oAuthResponse.getResponseStatus());
            response.sendRedirect(locationUri);
        } catch (IOException e) {
            throw new IllegalStateException("Write OAuthResponse error", e);
        }
    }


}
