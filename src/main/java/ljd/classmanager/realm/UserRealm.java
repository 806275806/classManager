package ljd.classmanager.realm;


import ljd.classmanager.Entity.PermissionEntity;
import ljd.classmanager.Entity.RoleEntity;
import ljd.classmanager.Entity.UserEntity;
import ljd.classmanager.Service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @program: springboot-shiro-demo
 * @description:
 * @author: liu yan
 * @create: 2019-11-15 20:04
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权");
        //获取用户主体
        Subject subject = SecurityUtils.getSubject();
        //这里获取的是登录的用户主体，拿不到Roles
        UserEntity user = (UserEntity)subject.getPrincipal();
        //下面user1获取的是多表查询的信息，拿到Roles
        UserEntity user1=userService.findByRolePerm(user.getUserCode());
        if(user1 != null){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            // 角色与权限字符串集合
            Collection<String> rolesCollection = new HashSet<>();
            Collection<String> premissionCollection = new HashSet<>();
            Set<RoleEntity> roles = user1.getRoles();
            for(RoleEntity role : roles){
                System.out.println(role.getRole());
                rolesCollection.add(role.getRole());
                Set<PermissionEntity> permissions = role.getPermissionEntities();
                for (PermissionEntity permission : permissions){
                    System.out.println(permission.getPermUrl());
                    premissionCollection.add(permission.getPermUrl());
                }
                //添加用户所属一个角色的所有权限
                System.out.println("测试有没有走到");
                info.addStringPermissions(premissionCollection);
            }
            //添加用户的所有角色
            info.addRoles(rolesCollection);
            return info;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证");
        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
        UserEntity userEntity = userService.Byusername(token.getUsername());
        if (userEntity ==null){
            return null;
        }
        ByteSource credentialsSalt = ByteSource.Util.bytes(userEntity.getUserCode());
        return new SimpleAuthenticationInfo(userEntity, userEntity.getUserPassword(),credentialsSalt,getName());
    }
}