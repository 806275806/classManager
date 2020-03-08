package ljd.classmanager.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

@TableName("attendance")
public class AttendanceEntity {

  private long id;
  private String attendanceId;
  private String courseCode;
  private String sNo;
  private String attendanceResult;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
  private java.sql.Timestamp attendanceDate;
  private String attendanceClass;
  private String attendanceType;

  public String getsNo() {
    return sNo;
  }

  public void setsNo(String sNo) {
    this.sNo = sNo;
  }



  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getAttendanceId() {
    return attendanceId;
  }

  public void setAttendanceId(String attendanceId) {
    this.attendanceId = attendanceId;
  }


  public String getCourseCode() {
    return courseCode;
  }

  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
  }


  public String getSNo() {
    return sNo;
  }

  public void setSNo(String sNo) {
    this.sNo = sNo;
  }


  public String getAttendanceResult() {
    return attendanceResult;
  }

  public void setAttendanceResult(String attendanceResult) {
    this.attendanceResult = attendanceResult;
  }


  public java.sql.Timestamp getAttendanceDate() {
    return attendanceDate;
  }

  public void setAttendanceDate(java.sql.Timestamp attendanceDate) {
    this.attendanceDate = attendanceDate;
  }


  public String getAttendanceClass() {
    return attendanceClass;
  }

  public void setAttendanceClass(String attendanceClass) {
    this.attendanceClass = attendanceClass;
  }


  public String getAttendanceType() {
    return attendanceType;
  }

  public void setAttendanceType(String attendanceType) {
    this.attendanceType = attendanceType;
  }



  @Override
  public String toString() {
    return "AttendanceEntity{" +
            "id=" + id +
            ", attendanceId='" + attendanceId + '\'' +
            ", courseCode='" + courseCode + '\'' +
            ", sNo='" + sNo + '\'' +
            ", attendanceResult='" + attendanceResult + '\'' +
            ", attendanceDate=" + attendanceDate +
            ", attendanceClass='" + attendanceClass + '\'' +
            ", attendanceType='" + attendanceType + '\'' +
            '}';
  }
}
