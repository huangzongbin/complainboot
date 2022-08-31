package com.krt.api.controller.v1;

import com.krt.api.dto.UserDTO;
import com.krt.common.annotation.KrtApiVersion;
import com.krt.common.annotation.KrtIgnoreAuth;
import com.krt.common.annotation.KrtLoginUser;
import com.krt.common.bean.ReturnBean;
import com.krt.common.bean.ReturnCode;
import com.krt.sys.entity.User;
import com.krt.sys.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;


/**
 * API测试接口接口
 *
 * @author 殷帅
 * @version 1.0
 * @date 2017年04月11日
 */
@KrtApiVersion(1)
@RestController
@RequestMapping("api/{version}")
@Api(tags = "3、测试接口API")
public class ApiV1TestController {

    @Autowired
    private IUserService userService;

    /**
     * 获取用户信息
     */
    @GetMapping("userInfo")
    @ApiOperation(value = "获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "version", value = "version", required = true),
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "accessToken")
    })
    public ReturnBean<UserDTO> userInfo(@ApiIgnore @KrtLoginUser User user) {
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            return ReturnBean.ok(userDTO);
        } else {
            return ReturnBean.error(ReturnCode.ACCESS_TOKEN_EXPIRE);
        }
    }

    /**
     * 忽略accessToken验证测试
     */
    @KrtIgnoreAuth
    @GetMapping("notToken")
    @ApiOperation(value = "忽略accessToken验证测试")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "version", value = "version", required = true)
    })
    public ReturnBean notToken() {
        return ReturnBean.ok("我是v1");
    }

    /**
     * accessToken验证测试
     */
    @GetMapping("needToken")
    @ApiOperation(value = "accessToken验证测试")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "version", value = "version", required = true),
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "accessToken")

    })
    public ReturnBean needToken(@ApiIgnore @KrtLoginUser User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return ReturnBean.ok(userDTO);
    }


    /**
     * accessToken 可选参数验证测试 有就验证 没有就不验证
     */
    @KrtIgnoreAuth(ignoreToken = false)
    @GetMapping("userList")
    @ApiOperation(value = "accessToken可选参数验证测试 有就验证 没有就不验证")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "version", value = "version", required = true),
            @ApiImplicitParam(paramType = "header", name = "accessToken", value = "accessToken")
    })
    public ReturnBean<List<UserDTO>> userList(@ApiIgnore @KrtLoginUser User user) {
        List userList = new ArrayList();
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            userList.add(userDTO);
        } else {
            List<User> uList = userService.selectList();
            for (User u : uList) {
                UserDTO userDTO = new UserDTO();
                BeanUtils.copyProperties(u, userDTO);
                userList.add(userDTO);
            }
        }
        return ReturnBean.ok(userList);
    }


}
