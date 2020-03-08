package ljd.classmanager.Service;


import ljd.classmanager.Entity.UserEntity;

import java.util.List;

/**
 * @program: springboot-shiro-demo
 * @description:
 * @author: liu yan
 * @create: 2019-11-16 22:12
 */
public interface UserService {
    UserEntity Byusername(String username);
    UserEntity findByRolePerm(String username);
    List<UserEntity> getUserList(UserEntity userEntity);
    Integer getUserTotal(UserEntity userEntity);
    Integer addUser(UserEntity userEntity);
    List<UserEntity> getpro(UserEntity userEntity);//获取存储过程结果
    Integer updateUser(UserEntity userEntity);
    Integer delUser(UserEntity userEntity);
}
