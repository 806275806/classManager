package ljd.classmanager.controller.wx;

import ljd.classmanager.Entity.AttendanceEntity;
import ljd.classmanager.Entity.CourseEntity;
import ljd.classmanager.Entity.CourseOfStuEntity;
import ljd.classmanager.Service.AttendanceHistoryService;
import ljd.classmanager.Service.AttendanceService;
import ljd.classmanager.Service.CourseofStuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-03-01 14:12
 */
@RestController
public class AttendanceController {
    @Autowired
    private CourseofStuService courseofStuService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private AttendanceHistoryService attendanceHistoryService;
    @Transactional
    @RequestMapping("/getCourseOfStuList")
    public List<CourseOfStuEntity> getCourseOfStu(@RequestBody CourseEntity courseEntity){
        System.out.println(courseEntity);
        return courseofStuService.getStuOfCourse(courseEntity);
    }
    @RequestMapping("/wechat/signIn")
    public Map<String,String> signIn(@RequestBody AttendanceEntity attendanceEntity){
        Map<String,String> map=new HashMap<>();
        Date date=new Date();
        Date now=new Date();
        System.out.println(attendanceEntity.getAttendanceId());
        date=attendanceHistoryService.getAttendanceTime(attendanceEntity.getAttendanceId()).get(0).getAttendanceTime();
        if (attendanceService.listHasSign(attendanceEntity).size()==0){//判断是否签到过
            if (attendanceService.signIn(attendanceEntity)!=0){//判断是否签到成功
                if (now.getTime()-date.getTime()>1*60*1000){//设置迟到时间
                    map.put("msg:","200");
                    map.put("attendanceResult","迟到");
                    map.put("toast","考勤完成");
                    attendanceEntity.setAttendanceResult("迟到");
                    attendanceService.signIn(attendanceEntity);
                }else {
                    map.put("msg:","200");
                    map.put("attendanceResult","已签到");
                    map.put("toast","考勤完成");
                }
            }else {
                map.put("msg:","200");
                map.put("attendanceResult:","未签到");
                map.put("toast","您不属于该课程");
            }
            System.out.println(attendanceEntity);
        }else {
            map.put("msg:","200");
            map.put("attendanceResult:","已签到");
            map.put("toast","不可重复签到");
        }
        return map;
    }
    @RequestMapping("/signByteacher")
    public Integer updateAttendance(@RequestBody AttendanceEntity attendanceEntity) {
        return attendanceService.updateAttendance(attendanceEntity);
    }
}