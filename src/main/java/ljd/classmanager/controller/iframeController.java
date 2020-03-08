package ljd.classmanager.controller;

import ljd.classmanager.Entity.AttendanceHistoryEntity;
import ljd.classmanager.Entity.CourseOfStuEntity;
import ljd.classmanager.Service.AttendanceHistoryService;
import ljd.classmanager.Service.CourseofStuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: classmanager
 * @description: iframe控制层
 * @author: liu yan
 * @create: 2019-12-19 18:36
 */
@Controller
public class iframeController {
    @Autowired
    private AttendanceHistoryService attendanceHistoryService;
    @Autowired
    private CourseofStuService courseofStuService;
    @RequestMapping("/myclass")
    public String myclass(){
        return "myclass";
    }
    @RequestMapping("/data")
    public String data(){
        return "data";
    }
    @RequestMapping("/user")
    public String user(){
        return "user";
    }
    @RequestMapping("/stu")
    public String stu(){
     return "students";
    }
    @RequestMapping("/toCourseDetail")
    public String toCourseDetails(){
        return "courseDetail";
    }
    @RequestMapping("/wechat/toQRCode")
    public String toQRCode(Model model,@RequestParam("attendanceId")String attendanceId, @RequestParam("signInType") String signInType){
        model.addAttribute("attendanceId",attendanceId);
        model.addAttribute("signInType",signInType);
        return "QRCode";
    }
    @RequestMapping("/wechat/toWechatScan")
    public String towechatScan(){
        return "wechatScan";
    }
    @RequestMapping("/getAttendanceStu")
    public String getAttendanceStu(String courseCode, String classNickname, String attendanceId,String attendanceResult,String sNo,String history,Model model){
        System.out.println("sss:"+sNo);
        model.addAttribute("attendnceStuList",courseofStuService.getAttendnceStu(courseCode,classNickname,attendanceId,attendanceResult,sNo));
        model.addAttribute("CourseStuList",courseofStuService.getCourseStu(courseCode,classNickname));
        model.addAttribute("attendanceResult",attendanceResult);
        if (history.equals("1")){
            model.addAttribute("Ishistory","IsHistory");
        }
        System.out.println("attendanceResult:"+model.getAttribute("attendanceResult"));
        if (attendanceResult!=null&&attendanceResult!=""){
            model.addAttribute("flag",1);//判断是否有学生考勤数据的标识
        }else {model.addAttribute("flag",0);}
        System.out.println("flag:"+model.getAttribute("flag"));
        System.out.println("attendnceStuList1:"+courseofStuService.getAttendnceStu(courseCode,classNickname,attendanceId,attendanceResult,sNo));
        System.out.println("attendnceStuList:"+model.getAttribute("attendnceStuList"));
        return "Tab_all";
    }
    @RequestMapping("/toHistory")
    public String toHistory(){
        return "attendanceHistory";
    }
}