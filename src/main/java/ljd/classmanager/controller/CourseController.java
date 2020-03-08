package ljd.classmanager.controller;

import ljd.classmanager.Entity.CourseEntity;
import ljd.classmanager.Entity.CourseOfClassEntity;
import ljd.classmanager.Entity.CourseOfStuEntity;
import ljd.classmanager.Entity.StudentsEntity;
import ljd.classmanager.Service.CourseService;
import ljd.classmanager.Service.CourseofClassService;
import ljd.classmanager.Service.CourseofStuService;
import ljd.classmanager.Service.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-02-02 16:14
 */
@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseofClassService courseofClassService;
    @Autowired
    private StudentsService studentsService;
    @Autowired
    private CourseofStuService courseofStuService;

    @RequestMapping("/getCourses")
    public List<CourseEntity> getCoursesList(String userCode, String courseState) {
        return courseService.getCoursesList(userCode, courseState);
    }

    @RequestMapping("/getStuNum")
    public List<CourseOfStuEntity> getStuNum(String courseCode, String classNickName) {
        return courseService.getStuNum(courseCode, classNickName);
    }

    @RequestMapping("/addCourse")
    @Transactional
    public int addCourse(@RequestBody CourseEntity courseEntity) {
//        return courseService.addCourse(courseEntity);
//        System.out.println(courseEntity);
        List<CourseOfClassEntity> List = new ArrayList<>(courseEntity.getClasses());
//        System.out.println("classes:"+List);
        List<StudentsEntity> stuList = new ArrayList<>();
        CourseOfStuEntity courseOfStuEntity = new CourseOfStuEntity();
        if (courseService.addCourse(courseEntity) != 0) {
            for (int i = 0; i < List.size(); i++) {
                courseofClassService.addCourseofClass(List.get(i));
                stuList = studentsService.getStuByClassCode(List.get(i).getClassCode());
//                System.out.println("stuList:"+stuList);
                for (int j = 0; j < stuList.size(); j++) {
                    courseOfStuEntity.setSNo(stuList.get(j).getsNo());
                    courseOfStuEntity.setCourseCode(List.get(i).getCourseCode());
                    courseOfStuEntity.setClassCode(List.get(i).getClassCode());
                    courseOfStuEntity.setAbsentNumber(0);
                    courseOfStuEntity.setLateNumber(0);
                    courseOfStuEntity.setLeaveNumber(0);
                    courseOfStuEntity.setClassNickname(List.get(i).getClassNickname());
//                    System.out.println(courseOfStuEntity);
                    courseofStuService.addStuToCourse(courseOfStuEntity);
                }
            }
        }
        return 0;
    }

    @RequestMapping("/updateCourse")
    @Transactional
    public int updateCourse(@RequestBody CourseEntity courseEntity) {
        List<StudentsEntity> stuList = new ArrayList<>();
        CourseOfStuEntity courseOfStuEntity = new CourseOfStuEntity();
        List<CourseOfClassEntity> ListFromWeb = new ArrayList<>(courseEntity.getClasses());
        List<CourseOfClassEntity> ListFromDataBase = new ArrayList<>(courseofClassService.getClassListByCourseCode(courseEntity.getCourseCode()));
        List<String> classCode_fromWeb = new ArrayList<>();
        List<String> classCode_fromDataBase = new ArrayList<>();
//        System.out.println(ListFromDataBase.get(0));
        for (int i = 0; i < ListFromDataBase.size(); i++) {
            classCode_fromDataBase.add(ListFromDataBase.get(i).getClassCode());
        }
        for (int i = 0; i < ListFromWeb.size(); i++) {
            classCode_fromWeb.add(ListFromWeb.get(i).getClassCode());
        }
        for (int i = 0; i < classCode_fromDataBase.size(); i++) {
            for (int j = 0; j < classCode_fromWeb.size(); j++) {
                try {
                    if (classCode_fromDataBase.contains(classCode_fromWeb.get(j))) {
                        System.out.println("执行了更新");
                        courseofClassService.updateCourseofClassByClassCode(ListFromWeb.get(j));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    return -1;
                }
            }
        }
        for (int i = 0; i < classCode_fromWeb.size(); i++) {
            for (int j = 0; j < classCode_fromDataBase.size(); j++) {
                if (!classCode_fromDataBase.contains(classCode_fromWeb.get(i))) {
                    System.out.println("执行了插入");
                    courseofClassService.insertCourseofClass(ListFromWeb.get(i));
                    stuList = studentsService.getStuByClassCode(ListFromWeb.get(i).getClassCode());
//                System.out.println("stuList:"+stuList);
                    for (int k = 0; k < stuList.size(); k++) {
                        courseOfStuEntity.setSNo(stuList.get(k).getsNo());
                        courseOfStuEntity.setCourseCode(ListFromWeb.get(i).getCourseCode());
                        courseOfStuEntity.setClassCode(ListFromWeb.get(i).getClassCode());
                        courseOfStuEntity.setAbsentNumber(0);
                        courseOfStuEntity.setLateNumber(0);
                        courseOfStuEntity.setLeaveNumber(0);
                        courseOfStuEntity.setClassNickname(ListFromWeb.get(i).getClassNickname());
//                    System.out.println(courseOfStuEntity);
                        System.out.println("执行了插入学生信息");
                        courseofStuService.addStuToCourse(courseOfStuEntity);
                    }
                }
            }
        }
        for (int i = 0; i < classCode_fromDataBase.size(); i++) {
            for (int j = 0; j < classCode_fromWeb.size(); j++) {
                //执行删除上课班级数据
                if (!classCode_fromWeb.contains(classCode_fromDataBase.get(i))) {
                    System.out.println("执行了删除");
                    courseofClassService.delCourseofClassByClassCode(ListFromDataBase.get(i));
                    courseofStuService.delStuinCourse(ListFromDataBase.get(i).getCourseCode(), ListFromDataBase.get(i).getClassCode());
                }
            }
        }
        return courseService.updateCourse(courseEntity);
    }

    @RequestMapping("/delCourse")
    public int delCourse(@RequestBody CourseEntity courseEntity){
        return courseService.delCourse(courseEntity);
    }
    @RequestMapping("/setCourseEnd")
    public int setCourseEnd(@RequestBody CourseEntity courseEntity){
        return courseService.setCourseEnd(courseEntity);
    }
    @RequestMapping("/setCourseNotEnd")
    public int setCourseNotEnd(@RequestBody CourseEntity courseEntity){
        return courseService.setCourseNotEnd(courseEntity);
    }
    @RequestMapping("/getCoursesListByterm")
    public List<CourseEntity> getCoursesListByterm(String userCode, String courseState, String courseTerm){
        System.out.println(courseTerm);
        return courseService.getCoursesListByterm(userCode,courseState,courseTerm);
    }
}