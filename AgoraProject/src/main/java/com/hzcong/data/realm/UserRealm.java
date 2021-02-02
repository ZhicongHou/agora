package com.hzcong.data.realm;

import com.hzcong.data.entities.UserEntity;
import com.hzcong.springboot.service.UserService;
import com.hzcong.util.PasswordHelper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    // 用户对应的角色信息与权限信息都保存在数据库中，通过UserService获取数据
//    private UserService userService = new UserServiceImpl();

    /**
     * 提供用户信息返回权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 根据用户名查询当前用户拥有的角色
        Iterable<String> roles = userService.getRoleSignsByUserName(userName);
        Set<String> roleNames = new HashSet<>();
//        System.out.println(userName+"has roles:");
        for (String roleName : roles) {
            roleNames.add(roleName);
//            System.out.println(roleName);
        }
        // 将角色名称提供给info
        authorizationInfo.setRoles(roleNames);
        // 根据用户名查询当前用户权限
//        Set<Permission> permissions = userService.findPermissions(username);
//        Set<String> permissionNames = new HashSet<String>();
//        for (Permission permission : permissions) {
//            permissionNames.add(permission.getPermission());
//        }
//        // 将权限名称提供给info
//        authorizationInfo.setStringPermissions(permissionNames);
        return authorizationInfo;
    }

    /**
     * 提供账户信息返回认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        UserEntity user = userService.getUserByUserName(userName);
        System.out.println("fuckcccccccccccccccccccccccccc"+"has roles:");
        if (user==null) {
            // 用户名不存在抛出异常
            throw new UnknownAccountException();
        }
//        if (user.getLocked() == 0) {
//            // 用户被管理员锁定抛出异常
//            throw new LockedAccountException();
//        }
        String salt = PasswordHelper.getSaltString(userName);
        //authenticationInfo使用数据库的信息生成
        //ByteSource.Util.bytes(salt)的默认编码格式是utf-8
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUserName(),
                user.getPassword(), ByteSource.Util.bytes(salt), getName());
        return authenticationInfo;
    }
}