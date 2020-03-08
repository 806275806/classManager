package ljd.classmanager.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ljd.classmanager.Entity.CourseEntity;
import ljd.classmanager.Entity.CourseOfClassEntity;
import ljd.classmanager.Entity.CourseOfStuEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: classmanager
 * @description: 课程Dao类
 * @author: liu yan
 * @create: 2020-02-02 15:50
 */
public interface CourseDao extends BaseMapper<CourseEntity> {
    List<CourseEntity> getCoursesList(@Param("userCode") String userCode, @Param("courseState") String courseState);
    List<CourseEntity> getCoursesListByterm(@Param("userCode") String userCode, @Param("courseState") String courseState,@Param("courseTerm")String courseTerm);
    List<CourseOfStuEntity> getStuNum(@Param("courseCode")String courseCode,@Param("classNickName")String classNickName);
    List<CourseEntity> getCourseBySno(@Param("Sno")String Sno,@Param("courseState")String courseState);
    int addCourse(CourseEntity courseEntity);
    int updateCourse(CourseEntity courseEntity);
}
