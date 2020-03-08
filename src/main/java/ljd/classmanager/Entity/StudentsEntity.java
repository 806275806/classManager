package ljd.classmanager.Entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import ljd.classmanager.Page.Page;
@TableName("students")
public class StudentsEntity extends Page {

  private Integer deptId;
  private String sGrade;
  private String majorCode;
  private String classCode;
  private String sNo;
  private String sName;
  private String sGender;
  private String sOpenid;
  private String sPicture;
  private String sTel;
  @TableField(exist = false)
  private String deptName;
  @TableField(exist = false)
  private String majorName;
  @TableField(exist = false)
  private String className;
  @TableField(exist = false)
  private String sessionId;

  public Integer getDeptId() {
    return deptId;
  }

  public void setDeptId(Integer deptId) {
    this.deptId = deptId;
  }

  public String getsGrade() {
    return sGrade;
  }

  public void setsGrade(String sGrade) {
    this.sGrade = sGrade;
  }

  public String getMajorCode() {
    return majorCode;
  }

  public void setMajorCode(String majorCode) {
    this.majorCode = majorCode;
  }

  public String getClassCode() {
    return classCode;
  }

  public void setClassCode(String classCode) {
    this.classCode = classCode;
  }

  public String getsNo() {
    return sNo;
  }

  public void setsNo(String sNo) {
    this.sNo = sNo;
  }

  public String getsName() {
    return sName;
  }

  public void setsName(String sName) {
    this.sName = sName;
  }

  public String getsGender() {
    return sGender;
  }

  public void setsGender(String sGender) {
    this.sGender = sGender;
  }

  public String getsOpenid() {
    return sOpenid;
  }

  public void setsOpenid(String sOpenid) {
    this.sOpenid = sOpenid;
  }

  public String getsPicture() {
    return sPicture;
  }

  public void setsPicture(String sPicture) {
    this.sPicture = sPicture;
  }

  public String getsTel() {
    return sTel;
  }

  public void setsTel(String sTel) {
    this.sTel = sTel;
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

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  @Override
  public String toString() {
    return "StudentsEntity{" +
            "deptId=" + deptId +
            ", sGrade='" + sGrade + '\'' +
            ", majorCode='" + majorCode + '\'' +
            ", classCode='" + classCode + '\'' +
            ", sNo='" + sNo + '\'' +
            ", sName='" + sName + '\'' +
            ", sGender='" + sGender + '\'' +
            ", sOpenid='" + sOpenid + '\'' +
            ", sPicture='" + sPicture + '\'' +
            ", sTel='" + sTel + '\'' +
            ", deptName='" + deptName + '\'' +
            ", majorName='" + majorName + '\'' +
            ", className='" + className + '\'' +
            '}';
  }
}
