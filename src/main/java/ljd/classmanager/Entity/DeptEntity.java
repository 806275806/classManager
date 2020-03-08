package ljd.classmanager.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.HashSet;
import java.util.Set;

@TableName("dept")
public class DeptEntity {

  private Integer deptId;
  private String deptName;
  @TableField(exist = false)
  private Set<MajorEntity> majors=new HashSet<>();

  public Set<MajorEntity> getMajors() {
    return majors;
  }

  public void setMajors(Set<MajorEntity> majors) {
    this.majors = majors;
  }

  public Integer getDeptId() {
    return deptId;
  }

  public void setDeptId(Integer deptId) {
    this.deptId = deptId;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  @Override
  public String toString() {
    return "DeptEntity{" +
            "deptId=" + deptId +
            ", deptName='" + deptName + '\'' +
            ", majors=" + majors +
            '}';
  }
}
