package ljd.classmanager.Entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import ljd.classmanager.Page.Page;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
@TableName("user")
public class UserEntity extends Page {

  private String userCode;
  private String userPassword;
  private Long deptId;
  private String userName;
  private String userGender;
  private String userTel;
  private String userOpenid;
  private Set<RoleEntity> roles=new HashSet<>();
  @TableField(exist = false)
  private String deptName;
  @TableField(exist = false)
  private String haveRole;
  @TableField(exist = false)
  private Integer haveRoleId;
  @TableField(exist = false)
  private List roleSelect;
  @TableField(exist = false)
  private String roleName;

  public Set<RoleEntity> getRoles() {
    return roles;
  }

  public void setRoles(Set<RoleEntity> roles) {
    this.roles = roles;
  }

  public String getUserCode() {
    return userCode;
  }

  public void setUserCode(String userCode) {
    this.userCode = userCode;
  }


  public String getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }


  public Long getDeptId() {
    return deptId;
  }

  public void setDeptId(Long deptId) {
    this.deptId = deptId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  public String getUserGender() {
    return userGender;
  }

  public void setUserGender(String userGender) {
    this.userGender = userGender;
  }


  public String getUserTel() {
    return userTel;
  }

  public void setUserTel(String userTel) {
    this.userTel = userTel;
  }


  public String getUserOpenid() {
    return userOpenid;
  }

  public void setUserOpenid(String userOpenid) {
    this.userOpenid = userOpenid;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  public String getHaveRole() {
    return haveRole;
  }

  public void setHaveRole(String haveRole) {
    this.haveRole = haveRole;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public Integer getHaveRoleId() {
    return haveRoleId;
  }

  public void setHaveRoleId(Integer haveRoleId) {
    this.haveRoleId = haveRoleId;
  }

  public List getRoleSelect() {
    return roleSelect;
  }

  public void setRoleSelect(List roleSelect) {
    this.roleSelect = roleSelect;
  }

  @Override
  public String toString() {
    return "UserEntity{" +
            "userCode='" + userCode + '\'' +
            ", userPassword='" + userPassword + '\'' +
            ", deptId=" + deptId +
            ", userName='" + userName + '\'' +
            ", userGender='" + userGender + '\'' +
            ", userTel='" + userTel + '\'' +
            ", userOpenid='" + userOpenid + '\'' +
            ", roles=" + roles +
            ", deptName='" + deptName + '\'' +
            ", haveRole='" + haveRole + '\'' +
            ", haveRoleId='" + haveRoleId + '\'' +
            ", roleSelect=" + roleSelect +
            ", roleName='" + roleName + '\'' +
            '}';
  }
}
