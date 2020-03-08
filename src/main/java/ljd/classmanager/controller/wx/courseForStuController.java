package ljd.classmanager.controller.wx;

import ljd.classmanager.Entity.CourseEntity;
import ljd.classmanager.Entity.CourseOfStuEntity;
import ljd.classmanager.Service.CourseService;
import ljd.classmanager.Service.CourseofStuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-02-28 17:19
 */
@RestController
@RequestMapping("/wechat")
public class courseForStuController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseofStuService courseofStuService;
    @RequestMapping("/getCourseBySno")
    public List<CourseEntity> getCourseBySno(String Sno,String courseState){
        System.out.println(courseService.getCourseBySno(Sno,courseState));
        return courseService.getCourseBySno(Sno,courseState);
    }
    @RequestMapping("/getAttendanceStuNum")
    public List<CourseOfStuEntity> getAttendanceStuNum(String courseCode, String classNickname, String attendanceId, String attendanceResult,String sNo){
        return courseofStuService.getAttendnceStu(courseCode,classNickname,attendanceId,attendanceResult,sNo);
    }
}