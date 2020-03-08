package ljd.classmanager.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import ljd.classmanager.Page.Page;

@TableName("course_of_stu")
public class CourseOfStuEntity extends Page{

  private long id;
  private String courseCode;
  private String classCode;
  private String sNo;
  private long lateNumber;
  private long leaveNumber;
  private long absentNumber;
  private String classNickname;

  @TableField(exist = false)
  private String deptName;
  @TableField(exist = false)
  private String sName;
  @TableField(exist = false)
  private String className;
  @TableField(exist = false)
  private String attendanceId;
  @TableField(exist = false)
  private String attendanceType;
  @TableField(exist = false)
  private String attendanceResult;
  @TableField(exist = false)
  private String attendanceDate;
  @TableField(exist = false)
  private String attendanceClass;
  @TableField(exist = false)
  private Integer offset;
  @TableField(exist = false)
  private Integer pageNumber;



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

  public String getSNo() {
    return sNo;
  }

  public void setSNo(String sNo) {
    this.sNo = sNo;
  }


  public long getLateNumber() {
    return lateNumber;
  }

  public void setLateNumber(long lateNumber) {
    this.lateNumber = lateNumber;
  }


  public long getLeaveNumber() {
    return leaveNumber;
  }

  public void setLeaveNumber(long leaveNumber) {
    this.leaveNumber = leaveNumber;
  }


  public long getAbsentNumber() {
    return absentNumber;
  }

  public void setAbsentNumber(long absentNumber) {
    this.absentNumber = absentNumber;
  }


  public String getClassNickname() {
    return classNickname;
  }

  public void setClassNickname(String classNickname) {
    this.classNickname = classNickname;
  }

  public String getsNo() {
    return sNo;
  }

  public void setsNo(String sNo) {
    this.sNo = sNo;
  }

  public String getDeptName() {
    return deptName;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  public String getsName() {
    return sName;
  }

  public void setsName(String sName) {
    this.sName = sName;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getAttendanceId() {
    return attendanceId;
  }

  public void setAttendanceId(String attendanceId) {
    this.attendanceId = attendanceId;
  }

  public String getAttendanceType() {
    return attendanceType;
  }

  public void setAttendanceType(String attendanceType) {
    this.attendanceType = attendanceType;
  }

  public String getAttendanceResult() {
    return attendanceResult;
  }

  public void setAttendanceResult(String attendanceResult) {
    this.attendanceResult = attendanceResult;
  }

  public String getAttendanceDate() {
    return attendanceDate;
  }

  public void setAttendanceDate(String attendanceDate) {
    this.attendanceDate = attendanceDate;
  }

  public String getAttendanceClass() {
    return attendanceClass;
  }

  public void setAttendanceClass(String attendanceClass) {
    this.attendanceClass = attendanceClass;
  }

  @Override
  public Integer getOffset() {
    return offset;
  }

  @Override
  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  @Override
  public Integer getPageNumber() {
    return pageNumber;
  }

  @Override
  public void setPageNumber(Integer pageNumber) {
    this.pageNumber = pageNumber;
  }

  @Override
  public String toString() {
    return "CourseOfStuEntity{" +
            "id=" + id +
            ", courseCode='" + courseCode + '\'' +
            ", classCode='" + classCode + '\'' +
            ", sNo='" + sNo + '\'' +
            ", lateNumber=" + lateNumber +
            ", leaveNumber=" + leaveNumber +
            ", absentNumber=" + absentNumber +
            ", classNickname='" + classNickname + '\'' +
            ", deptName='" + deptName + '\'' +
            ", sName='" + sName + '\'' +
            ", className='" + className + '\'' +
            ", attendanceId='" + attendanceId + '\'' +
            ", attendanceType='" + attendanceType + '\'' +
            ", attendanceResult='" + attendanceResult + '\'' +
            ", attendanceDate='" + attendanceDate + '\'' +
            ", attendanceClass='" + attendanceClass + '\'' +
            '}';
  }
}
