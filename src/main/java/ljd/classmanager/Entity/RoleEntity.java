package ljd.classmanager.Entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.HashSet;
import java.util.Set;
@TableName("role")
public class RoleEntity {

  private Integer roleId;
  private String role;
  @TableField(exist = false)
  private Set<PermissionEntity> permissionEntities=new HashSet<>();

  public Set<PermissionEntity> getPermissionEntities() {
    return permissionEntities;
  }

  public void setPermissionEntities(Set<PermissionEntity> permissionEntities) {
    this.permissionEntities = permissionEntities;
  }

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

}
