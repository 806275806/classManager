package ljd.classmanager.Entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.HashSet;
import java.util.Set;

@TableName("course")
public class CourseEntity {

  private String courseCode;
  private String courseType;
  private String courseName;
  private String userCode;
  private String courseTerm;
  private String courseState;
  @TableField(exist = false)
  private Set<CourseOfClassEntity> classes=new HashSet<>();
  @TableField(exist = false)
  private String classnickName;
  @TableField(exist = false)
  private String courseCode_1;//用于编辑时存放原来的课程编码
  public String getCourseCode() {
    return courseCode;
  }

  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
  }


  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }


  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }


  public String getUserCode() {
    return userCode;
  }

  public void setUserCode(String userCode) {
    this.userCode = userCode;
  }


  public String getCourseTerm() {
    return courseTerm;
  }

  public void setCourseTerm(String courseTerm) {
    this.courseTerm = courseTerm;
  }


  public String getCourseState() {
    return courseState;
  }

  public void setCourseState(String courseState) {
    this.courseState = courseState;
  }

    public Set<CourseOfClassEntity> getClasses() {
        return classes;
    }

    public void setClasses(Set<CourseOfClassEntity> classes) {
        this.classes = classes;
    }

    public String getClassnickName() {
        return classnickName;
    }

    public void setClassnickName(String classnickName) {
        this.classnickName = classnickName;
    }

  public String getCourseCode_1() {
    return courseCode_1;
  }

  public void setCourseCode_1(String courseCode_1) {
    this.courseCode_1 = courseCode_1;
  }

  @Override
  public String toString() {
    return "CourseEntity{" +
            "courseCode='" + courseCode + '\'' +
            ", courseType='" + courseType + '\'' +
            ", courseName='" + courseName + '\'' +
            ", userCode='" + userCode + '\'' +
            ", courseTerm='" + courseTerm + '\'' +
            ", courseState='" + courseState + '\'' +
            ", classes=" + classes +
            ", classnickName='" + classnickName + '\'' +
            ", courseCode_1='" + courseCode_1 + '\'' +
            '}';
  }
}
