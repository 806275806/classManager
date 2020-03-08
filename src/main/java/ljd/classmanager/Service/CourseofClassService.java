package ljd.classmanager.Service;

import ljd.classmanager.Entity.CourseOfClassEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-02-20 15:21
 */
public interface CourseofClassService {
    int addCourseofClass(CourseOfClassEntity courseOfClassEntity);
    int updateCourseofClassByClassCode(CourseOfClassEntity courseOfClassEntity);
    int delCourseofClassByClassCode(CourseOfClassEntity courseOfClassEntity);
    List<CourseOfClassEntity> getClassListByCourseCode(@Param("courseCode")String courseCode);
    int insertCourseofClass(CourseOfClassEntity courseOfClassEntity);
}
