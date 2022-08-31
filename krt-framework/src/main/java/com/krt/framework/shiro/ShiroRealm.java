package com.krt.framework.shiro;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.krt.common.constant.GlobalConstant;
import com.krt.common.validator.Assert;
import com.krt.sys.entity.Res;
import com.krt.sys.entity.Role;
import com.krt.sys.entity.User;
import com.krt.sys.enums.UserStatusEnum;
import com.krt.sys.service.IResService;
import com.krt.sys.service.IRoleService;
import com.krt.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;


/**
 * 自定义Realm
 *
 * @author 殷帅
 * @version 1.0
 * @date 2016年4月18日
 */
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Lazy
    @Autowired
    private IUserService userService;

    @Lazy
    @Autowired
    private IRoleService roleService;

    @Lazy
    @Autowired
    private IResService resService;


    /**
     * 为当前登录的Subject授予角色和权限
     *
     * @param principals {@link PrincipalCollection}
     * @return {@link AuthorizationInfo}
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.debug("==================为当前登录的Subject授予角色和权限=====================");
        //为当前用户设置角色和权限
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            //为当前用户设置角色
            simpleAuthorInfo.addRole(role.getCode());
        }
        //获取角色权限
        List<Res> list;
        if (GlobalConstant.ADMIN.equals(user.getUsername())) {
            list = resService.selectList();
        } else {
            list = resService.selectRolesPermission(roles);
        }
        if (list != null && list.size() > 0) {
            for (Res res : list) {
                String permission = res.getPermission();
                if (!Assert.isBlank(permission)) {
                    simpleAuthorInfo.addStringPermission(permission);
                }
            }
        }
        return simpleAuthorInfo;
    }


    /**
     * 验证当前登录的Subject
     *
     * @param authcToken 认证token
     * @return {@link AuthenticationInfo}
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        log.debug("==================验证当前登录的Subject=====================");
        // 将token装换成UsernamePasswordToken
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        // 查询数据库，是否查询到用户
        User user = userService.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, token.getUsername()));
        if (user != null) {
            //判断账号是否禁用
            if (UserStatusEnum.NORMAL.getValue().equals(user.getStatus())) {
                List<Role> roles;
                //是否是超管 获取用户角色
                if (GlobalConstant.ADMIN.equalsIgnoreCase(user.getUsername())) {
                    Role role = roleService.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getCode, GlobalConstant.ADMIN));
                    roles = new ArrayList<>();
                    roles.add(role);
                } else {
                    roles = roleService.selectUserRoles(user.getId());
                }
                user.setRoles(roles);
                //将账户名，密码，盐值，realmName实例化到SimpleAuthenticationInfo中交给Shiro来管理
                return new SimpleAuthenticationInfo(user,
                        user.getPassword(),
                        com.krt.common.shiro.ByteSourceUtils.bytes(user.getSalt()),
                        this.getName());
            } else {
                throw new LockedAccountException();
            }
        } else {
            throw new UnknownAccountException();
        }
    }

}