package ljd.classmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ljd.classmanager.Entity.*;
import ljd.classmanager.Page.PageHelper;
import ljd.classmanager.Service.AttendanceHistoryService;
import ljd.classmanager.Service.AttendanceService;
import ljd.classmanager.Service.CourseofStuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-03-02 00:29
 */
@RestController
public class AttendanceHistoryController {
    @Autowired
    private AttendanceHistoryService attendanceHistoryService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private CourseofStuService courseofStuService;
    @RequestMapping("/isSumbit")
    public List<AttendanceHistoryEntity> getHistoryList(@RequestBody AttendanceHistoryEntity attendanceHistoryEntity){
        System.out.println("ceshi:"+attendanceHistoryEntity);
        return attendanceHistoryService.getAttendanceHistory(attendanceHistoryEntity);
    }
    @RequestMapping("/editAttendance")
    public Integer updateAttendance(@RequestBody AttendanceHistoryEntity attendanceHistoryEntity){
        return attendanceHistoryService.updateAttendance(attendanceHistoryEntity);
    }
    @RequestMapping("/delAttendance")
    public Integer delAttendance(@RequestBody AttendanceHistoryEntity attendanceHistoryEntity){
        return attendanceHistoryService.delAttendance(attendanceHistoryEntity);
    }
    @RequestMapping("/addAttendance")
    //创建考勤,需要的参数：courseCode,classnickName,attendanceId,attendanceType
    public Integer addAttendance(@RequestBody AttendanceHistoryEntity attendanceHistoryEntity){
        if (attendanceHistoryService.addAttendanceHistory(attendanceHistoryEntity)!=0){
            CourseEntity courseEntity=new CourseEntity();//新建课程对象用于查询该课程的学生列表
            courseEntity.setCourseCode(attendanceHistoryEntity.getCourseCode());
            courseEntity.setClassnickName(attendanceHistoryEntity.getClassnickName());
            System.out.println("添加考勤的参数:"+attendanceHistoryEntity);
            List<CourseOfStuEntity> stuList=courseofStuService.getStuOfCourse(courseEntity);//查询该课程的学生列表
            AttendanceEntity attendanceEntity=new AttendanceEntity();//新建考勤对象用于将学生的考勤信息插入考勤表
            for (int i=0;i<stuList.size();i++){
                attendanceEntity.setAttendanceId(attendanceHistoryEntity.getAttendanceId());
                attendanceEntity.setCourseCode(attendanceHistoryEntity.getCourseCode());
                attendanceEntity.setSNo(stuList.get(i).getSNo());
                attendanceEntity.setAttendanceType(attendanceHistoryEntity.getAttendanceType());
                attendanceService.addAttendance(attendanceEntity);
            }
        }
        return 0;
    }
    @RequestMapping("/getSignInSum")
    public Integer getCountOfSignIn(String attendanceId) {
        return attendanceService.getCountOfSignIn(attendanceId);
    }
    @RequestMapping("/getHistory")
    public PageHelper<AttendanceHistoryEntity> getHistory(AttendanceHistoryEntity attendanceHistoryEntity){
        PageHelper<AttendanceHistoryEntity> pagehelper=new PageHelper<>();
        pagehelper.setRows(attendanceHistoryService.getHistory(attendanceHistoryEntity));
        pagehelper.setTotal(attendanceHistoryService.getHistoryCount(attendanceHistoryEntity));
        System.out.println(attendanceHistoryEntity);
        return pagehelper;
    }
    @RequestMapping("/getHistoryStu")
    public PageHelper<CourseOfStuEntity> getHistoryStu(CourseOfStuEntity courseOfStuEntity){
        System.out.println("ss:"+courseOfStuEntity);
        PageHelper<CourseOfStuEntity> pageHelper=new PageHelper<>();
        pageHelper.setRows(courseofStuService.getAttendnceStuToHistory(courseOfStuEntity));
        pageHelper.setTotal(courseofStuService.getAttendanceStuCount(courseOfStuEntity));
        return pageHelper;
    }
    @RequestMapping("/updateHistory")
    @Transactional
    public Boolean updateHistory(@RequestBody AttendanceHistoryEntity attendanceHistoryEntity){
        AttendanceEntity attendanceEntity=new AttendanceEntity();
        attendanceEntity.setAttendanceId(attendanceHistoryEntity.getAttendanceId());
        attendanceEntity.setAttendanceResult(attendanceHistoryEntity.getAttendanceResult());
        attendanceEntity.setSNo(attendanceHistoryEntity.getsNo());
        boolean flag=attendanceService.updateAttendance(attendanceEntity)!=0&&attendanceHistoryService.updateState(attendanceHistoryEntity)!=0;
        return flag;
    }
}