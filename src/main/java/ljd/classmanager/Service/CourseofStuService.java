package ljd.classmanager.Service;

import ljd.classmanager.Entity.CourseEntity;
import ljd.classmanager.Entity.CourseOfStuEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-02-20 22:36
 */
public interface CourseofStuService {
    int addStuToCourse(CourseOfStuEntity courseOfStuEntity);
    int delStuinCourse(String courseCode,String classCode);
    Integer getAttendanceStuCount(CourseOfStuEntity courseOfStuEntity);
    List<CourseOfStuEntity> getAttendnceStuToHistory(CourseOfStuEntity courseOfStuEntity);
    List<CourseOfStuEntity> getStuOfCourse(CourseEntity courseEntity);
    List<CourseOfStuEntity> getAttendnceStu(String courseCode, String classNickname, String attendanceId,String attendanceResult,String sNo);
    List<CourseOfStuEntity> getCourseStu(String courseCode, String classNickname);
}
