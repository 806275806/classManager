package ljd.classmanager.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ljd.classmanager.Entity.CourseOfClassEntity;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-02-20 15:20
 */
public interface CourseofClassDao extends BaseMapper<CourseOfClassEntity> {
    int insertCourseofClass(CourseOfClassEntity courseOfClassEntity);
}
