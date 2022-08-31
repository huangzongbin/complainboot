package com.krt.api.controller.v2;

import com.krt.common.annotation.KrtApiVersion;
import com.krt.common.annotation.KrtIgnoreAuth;
import com.krt.common.bean.ReturnBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * API测试接口接口
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年04月11日
 */
@KrtApiVersion(2)
@RestController
@RequestMapping("api/{version}/")
@Api(tags = "3、测试接口API")
public class ApiV2TestController {


    /**
     * 忽略accessToken验证测试
     */
    @KrtIgnoreAuth
    @GetMapping("notToken")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "version", value = "version", required = true)
    })
    public ReturnBean notToken() {
        return ReturnBean.ok("我是v2");
    }

    /**
     * 忽略accessToken验证测试
     */
    @Deprecated
    @KrtApiVersion(3)
    @KrtIgnoreAuth
    @GetMapping("notToken")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "version", value = "version", required = true)
    })
    public ReturnBean notToken3() {
        return ReturnBean.ok("我是v3");
    }

    /**
     * 忽略accessToken验证测试
     */
    @Deprecated
    @KrtApiVersion(value = 4,deprecated = true)
    @KrtIgnoreAuth
    @GetMapping("notToken")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "version", value = "version", required = true)
    })
    public ReturnBean notToken4() {
        return ReturnBean.ok("我是v4");
    }

}
