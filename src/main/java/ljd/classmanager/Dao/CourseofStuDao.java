package ljd.classmanager.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ljd.classmanager.Entity.CourseOfStuEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-02-20 22:35
 */
public interface CourseofStuDao extends BaseMapper<CourseOfStuEntity> {
 List<CourseOfStuEntity> getAttendnceStu(@Param("courseCode") String courseCode, @Param("classNickname")String classNickname, @Param("attendanceId")String attendanceId,@Param("attendanceResult")String attendanceResult,@Param("sNo")String sNo);
 Integer getAttendanceStuCount(CourseOfStuEntity courseOfStuEntity);
 List<CourseOfStuEntity> getAttendnceStuToHistory(CourseOfStuEntity courseOfStuEntity);
 List<CourseOfStuEntity> getCourseStu(@Param("courseCode") String courseCode, @Param("classNickname")String classNickname);
}
