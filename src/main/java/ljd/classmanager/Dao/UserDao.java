package ljd.classmanager.Dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ljd.classmanager.Entity.UserEntity;

import java.util.List;

/**
 * @program: springboot-shiro-demo
 * @description:
 * @author: liu yan
 * @create: 2019-11-16 22:05
 */

public interface UserDao extends BaseMapper<UserEntity> {
     UserEntity Byusername(String username);
     UserEntity findByRolePerm(String username);
     List<UserEntity> getUserList(UserEntity userEntity);
     Integer getUserTotal(UserEntity userEntity);
     Integer addUser(UserEntity userEntity);
     List<UserEntity> getpro(UserEntity userEntity);
     Integer updateUser(UserEntity userEntity);
}