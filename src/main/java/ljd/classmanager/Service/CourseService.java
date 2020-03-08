package ljd.classmanager.Service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ljd.classmanager.Entity.ClassEntity;
import ljd.classmanager.Entity.CourseEntity;
import ljd.classmanager.Entity.CourseOfClassEntity;
import ljd.classmanager.Entity.CourseOfStuEntity;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-02-02 15:59
 */
public interface CourseService{
    List<CourseEntity> getCoursesList(String userCode, String courseState);
    List<CourseOfStuEntity> getStuNum(String courseCode,String classNickName);
    List<CourseEntity> getCoursesListByterm(String userCode, String courseState,String courseTerm);
    List<CourseEntity> getCourseBySno(String Sno,String courseState);
    int addCourse(CourseEntity courseEntity);
    int updateCourse(CourseEntity courseEntity);
    int delCourse(CourseEntity courseEntity);
    int setCourseEnd(CourseEntity courseEntity);
    int setCourseNotEnd(CourseEntity courseEntity);
}
