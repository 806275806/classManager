package ljd.classmanager.Service.ServiceImpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ljd.classmanager.Dao.UserDao;
import ljd.classmanager.Entity.UserEntity;
import ljd.classmanager.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: springboot-shiro-demo
 * @description:
 * @author: liu yan
 * @create: 2019-11-16 22:15
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public UserEntity Byusername(String username) {
        return userDao.Byusername(username);
    }

    @Override
    public UserEntity findByRolePerm(String username) {
        return userDao.findByRolePerm(username);
    }

    @Override
    public List<UserEntity> getUserList(UserEntity userEntity) {
        return userDao.getUserList(userEntity);
    }

    @Override
    public Integer getUserTotal(UserEntity userEntity) {
        return userDao.getUserTotal(userEntity);
    }

    @Override
    public Integer addUser(UserEntity userEntity) {
        return userDao.addUser(userEntity);
    }

    @Override
    public List<UserEntity> getpro(UserEntity userEntity) {
        return userDao.getpro(userEntity);
    }

    @Override
    public Integer updateUser(UserEntity userEntity) {
        return userDao.updateUser(userEntity);
    }

    @Override
    public Integer delUser(UserEntity userEntity) {
        QueryWrapper<UserEntity> wrapper=new QueryWrapper<>();
        wrapper.eq("user_code",userEntity.getUserCode());
        return userDao.delete(wrapper);
    }


}