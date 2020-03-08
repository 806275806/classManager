package ljd.classmanager.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ljd.classmanager.Dao.RoleDao;
import ljd.classmanager.Entity.RoleEntity;
import ljd.classmanager.Entity.UserEntity;
import ljd.classmanager.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-01-11 22:14
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Override
    public List<RoleEntity> getRoleList() {
        QueryWrapper<RoleEntity> wrapper=new QueryWrapper<>();
        return roleDao.selectList(wrapper);
    }

    @Override
    public Integer addRole(UserEntity userEntity) {
        return roleDao.addRole(userEntity);
    }

    @Override
    public List<RoleEntity> getRoleByUserCode(String userCode) {
        return roleDao.getRoleByUserCode(userCode);
    }

    @Override
    public Integer delUser_Role(String userCode, Integer roleId) {
        return roleDao.delUser_Role(userCode,roleId);
    }
}