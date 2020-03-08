package ljd.classmanager.Entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("major")
public class MajorEntity {

  private String majorCode;
  private Integer deptId;
  private String majorName;


  public String getMajorCode() {
    return majorCode;
  }

  public void setMajorCode(String majorCode) {
    this.majorCode = majorCode;
  }


  public long getDeptId() {
    return deptId;
  }


  public void setDeptId(Integer deptId) {
    this.deptId = deptId;
  }

  public String getMajorName() {
    return majorName;
  }

  public void setMajorName(String majorName) {
    this.majorName = majorName;
  }

}
