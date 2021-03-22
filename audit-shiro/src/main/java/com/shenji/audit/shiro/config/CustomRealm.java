package com.shenji.audit.shiro.config;

import com.shenji.audit.model.User;
import com.shenji.audit.shiro.Permission;
import com.shenji.audit.shiro.Role;
import com.shenji.audit.shiro.service.ShiroService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * shiro获取用户信息的域
 *
 * @author misxr
 * @version 1.0
 * @date 2021/3/15 21:52
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private ShiroService shiroService;

    /**
     * @MethodName doGetAuthorizationInfo
     * @Description 鉴权
     * @Param [principalCollection]
     * @Return AuthorizationInfo
     * @Author misxr
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String name = (String) principalCollection.getPrimaryPrincipal();
        User user = shiroService.getUserByUsername(name);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoles()) {
            simpleAuthorizationInfo.addRole(role.getName());
            for (Permission permission : role.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permission.getName());
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * @MethodName doGetAuthenticationInfo
     * @Description 认证
     * @Param [authenticationToken]
     * @Return AuthenticationInfo
     * @Author misxr
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (StringUtils.isEmpty(authenticationToken.getPrincipal())) {
            return null;
        }
        String name = authenticationToken.getPrincipal().toString();
        User user = shiroService.getUserByUsername(name);
        if (user == null) {
            return null;
        } else {
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, user.getPassword(), getName());
            return simpleAuthenticationInfo;
        }
    }
}
