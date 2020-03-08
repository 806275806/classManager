package ljd.classmanager.Entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import ljd.classmanager.Page.Page;
import ljd.classmanager.Page.PageHelper;

import java.sql.Date;
import java.sql.Timestamp;

@TableName("attendance_history")
public class AttendanceHistoryEntity extends Page {

  private String attendanceId;
  private long attendanceSignin;
  private long attendanceLate;
  private long attendanceLeave;
  private long attendanceAbsent;
  private long attendanceNosign;
  private String attendanceState;
  private String attendanceType;
  private Date attendanceTime;
  @TableField(exist = false)
  private String courseCode;
  @TableField(exist = false)
  private String classnickName;
  @TableField(exist = false)
  private String attendanceResult;
  @TableField
  private String dateOne;
  @TableField
  private String dateTwo;
  @TableField
  private String stateOne;
  @TableField
  private String stateTwo;
  @TableField
  private String sNo;
  public String getAttendanceId() {
    return attendanceId;
  }

  public void setAttendanceId(String attendanceId) {
    this.attendanceId = attendanceId;
  }


  public long getAttendanceSignin() {
    return attendanceSignin;
  }

  public void setAttendanceSignin(long attendanceSignin) {
    this.attendanceSignin = attendanceSignin;
  }

  public long getAttendanceLate() {
    return attendanceLate;
  }

  public void setAttendanceLate(long attendanceLate) {
    this.attendanceLate = attendanceLate;
  }


  public long getAttendanceLeave() {
    return attendanceLeave;
  }

  public void setAttendanceLeave(long attendanceLeave) {
    this.attendanceLeave = attendanceLeave;
  }


  public long getAttendanceAbsent() {
    return attendanceAbsent;
  }

  public void setAttendanceAbsent(long attendanceAbsent) {
    this.attendanceAbsent = attendanceAbsent;
  }

  public long getAttendanceNosign() {
    return attendanceNosign;
  }

  public void setAttendanceNosign(long attendanceNosign) {
    this.attendanceNosign = attendanceNosign;
  }

  public String getAttendanceState() {
    return attendanceState;
  }

  public void setAttendanceState(String attendanceState) {
    this.attendanceState = attendanceState;
  }

  public String getAttendanceType() {
    return attendanceType;
  }

  public void setAttendanceType(String attendanceType) {
    this.attendanceType = attendanceType;
  }

//  public Timestamp getAttendanceTime() {
//    return attendanceTime;
//  }
//
//  public void setAttendanceTime(Timestamp attendanceTime) {
//    this.attendanceTime = attendanceTime;
//  }


  public Date getAttendanceTime() {
    return attendanceTime;
  }

  public void setAttendanceTime(Date attendanceTime) {
    this.attendanceTime = attendanceTime;
  }

  public String getCourseCode() {
    return courseCode;
  }

  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
  }

  public String getClassnickName() {
    return classnickName;
  }

  public void setClassnickName(String classnickName) {
    this.classnickName = classnickName;
  }

  public String getAttendanceResult() {
    return attendanceResult;
  }

  public void setAttendanceResult(String attendanceResult) {
    this.attendanceResult = attendanceResult;
  }

  public String getDateOne() {
    return dateOne;
  }

  public void setDateOne(String dateOne) {
    this.dateOne = dateOne;
  }

  public String getDateTwo() {
    return dateTwo;
  }

  public void setDateTwo(String dateTwo) {
    this.dateTwo = dateTwo;
  }

  public String getStateOne() {
    return stateOne;
  }

  public void setStateOne(String stateOne) {
    this.stateOne = stateOne;
  }

  public String getStateTwo() {
    return stateTwo;
  }

  public void setStateTwo(String stateTwo) {
    this.stateTwo = stateTwo;
  }

  public String getsNo() {
    return sNo;
  }

  public void setsNo(String sNo) {
    this.sNo = sNo;
  }

  @Override
  public String toString() {
    return "AttendanceHistoryEntity{" +
            "attendanceId='" + attendanceId + '\'' +
            ", attendanceSignin=" + attendanceSignin +
            ", attendanceLate=" + attendanceLate +
            ", attendanceLeave=" + attendanceLeave +
            ", attendanceAbsent=" + attendanceAbsent +
            ", attendanceState='" + attendanceState + '\'' +
            ", attendanceType='" + attendanceType + '\'' +
            ", attendanceTime=" + attendanceTime +
            ", courseCode='" + courseCode + '\'' +
            ", classnickName='" + classnickName + '\'' +
            ", attendanceResult='" + attendanceResult + '\'' +
            ", dateOne='" + dateOne + '\'' +
            ", dateTwo='" + dateTwo + '\'' +
            '}';
  }
}
