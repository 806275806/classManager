package ljd.classmanager.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("course_of_class")
public class CourseOfClassEntity {

  private long id;
  private String courseCode;
  private String classCode;
  private String classNickname;
  private Integer deptId;
  @TableField(exist = false)
  private String allClass;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getCourseCode() {
    return courseCode;
  }

  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
  }


  public String getClassCode() {
    return classCode;
  }

  public void setClassCode(String classCode) {
    this.classCode = classCode;
  }


  public String getClassNickname() {
    return classNickname;
  }

  public void setClassNickname(String classNickname) {
    this.classNickname = classNickname;
  }

  public Integer getDeptId() {
    return deptId;
  }

  public void setDeptId(Integer deptId) {
    this.deptId = deptId;
  }

  public String getAllClass() {
    return allClass;
  }

  public void setAllClass(String allClass) {
    this.allClass = allClass;
  }

  @Override
  public String toString() {
    return "CourseOfClassEntity{" +
            "id=" + id +
            ", courseCode='" + courseCode + '\'' +
            ", classCode='" + classCode + '\'' +
            ", classNickname='" + classNickname + '\'' +
            ", deptId=" + deptId +
            ", allClass='" + allClass + '\'' +
            '}';
  }
}
