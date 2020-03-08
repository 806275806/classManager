package ljd.classmanager.Service;

import ljd.classmanager.Entity.RoleEntity;
import ljd.classmanager.Entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-01-11 22:13
 */
public interface RoleService {
    List<RoleEntity> getRoleList();
    Integer addRole(UserEntity userEntity);
    List<RoleEntity> getRoleByUserCode(@Param("userCode") String userCode);
    Integer delUser_Role(@Param("userCode")String userCode,@Param("roleId")Integer roleId);
}
