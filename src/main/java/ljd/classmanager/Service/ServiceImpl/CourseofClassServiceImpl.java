package ljd.classmanager.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import ljd.classmanager.Dao.CourseofClassDao;
import ljd.classmanager.Entity.CourseOfClassEntity;
import ljd.classmanager.Service.CourseofClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-02-20 15:22
 */
@Service
public class CourseofClassServiceImpl implements CourseofClassService {
@Autowired
    CourseofClassDao courseofClassDao;

    @Override
    public int addCourseofClass(CourseOfClassEntity courseOfClassEntity) {
        return courseofClassDao.insert(courseOfClassEntity);
    }

    @Override
    public int updateCourseofClassByClassCode(CourseOfClassEntity courseOfClassEntity) {
        UpdateWrapper wrapper=new UpdateWrapper();
        wrapper.eq("course_code",courseOfClassEntity.getCourseCode());
        wrapper.eq("class_code",courseOfClassEntity.getClassCode());
        wrapper.setEntity(courseOfClassEntity);
        return courseofClassDao.update(courseOfClassEntity,wrapper);
    }

    @Override
    public int delCourseofClassByClassCode(CourseOfClassEntity courseOfClassEntity) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("course_code",courseOfClassEntity.getCourseCode());
        wrapper.eq("class_code",courseOfClassEntity.getClassCode());
        return courseofClassDao.delete(wrapper);
    }

    @Override
    public List<CourseOfClassEntity> getClassListByCourseCode(String courseCode) {
        QueryWrapper<CourseOfClassEntity> wrapper=new QueryWrapper<>();
        wrapper.eq("course_code",courseCode);
        return courseofClassDao.selectList(wrapper);
    }

    @Override
    public int insertCourseofClass(CourseOfClassEntity courseOfClassEntity) {
        return courseofClassDao.insertCourseofClass(courseOfClassEntity);
    }
}