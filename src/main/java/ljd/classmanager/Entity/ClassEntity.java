package ljd.classmanager.Entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import ljd.classmanager.Page.Page;

@TableName("class")
public class ClassEntity extends Page{

  private String classCode;
  private String majorCode;
  private String className;
  private String classGrade;
  @TableField(exist = false)
  private String deptName;
  @TableField(exist = false)
  private String majorName;
  @TableField(exist = false)
  private String deptId;
  @TableField(exist = false)
  private String id;

  public String getClassCode() {
    return classCode;
  }

  public void setClassCode(String classCode) {
    this.classCode = classCode;
  }


  public String getMajorCode() {
    return majorCode;
  }

  public void setMajorCode(String majorCode) {
    this.majorCode = majorCode;
  }


  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }


  public String getClassGrade() {
    return classGrade;
  }

  public void setClassGrade(String classGrade) {
    this.classGrade = classGrade;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  public String getMajorName() {
    return majorName;
  }

  public void setMajorName(String majorName) {
    this.majorName = majorName;
  }

  public String getDeptId() {
    return deptId;
  }

  public void setDeptId(String deptId) {
    this.deptId = deptId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
