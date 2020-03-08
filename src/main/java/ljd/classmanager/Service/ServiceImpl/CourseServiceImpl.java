package ljd.classmanager.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import ljd.classmanager.Dao.CourseDao;
import ljd.classmanager.Entity.CourseEntity;
import ljd.classmanager.Entity.CourseOfStuEntity;
import ljd.classmanager.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-02-02 16:12
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;
    @Override
    public List<CourseEntity> getCoursesList(String userCode, String courseState) {
        return courseDao.getCoursesList(userCode,courseState);
    }

    @Override
    public List<CourseOfStuEntity> getStuNum(String courseCode, String classNickName) {
        return courseDao.getStuNum(courseCode,classNickName);
    }

    @Override
    public List<CourseEntity> getCoursesListByterm(String userCode, String courseState, String courseTerm) {
        return courseDao.getCoursesListByterm(userCode,courseState,courseTerm);
    }

    @Override
    public List<CourseEntity> getCourseBySno(String Sno,String courseType) {
        return courseDao.getCourseBySno(Sno,courseType);
    }

    @Override
    public int addCourse(CourseEntity courseEntity) {
        return courseDao.addCourse(courseEntity);
    }

    @Override
    public int updateCourse(CourseEntity courseEntity) {
        return courseDao.updateCourse(courseEntity);
    }

    @Override
    public int delCourse(CourseEntity courseEntity) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("course_code",courseEntity.getCourseCode());
        return courseDao.delete(wrapper);
    }

    @Override
    public int setCourseEnd(CourseEntity courseEntity) {
        String state="已结课";
        UpdateWrapper<CourseEntity> wrapper=new UpdateWrapper<>();
        wrapper.eq("course_code",courseEntity.getCourseCode());
        wrapper.set("course_state",state);
        return courseDao.update(courseEntity,wrapper);
    }

    @Override
    public int setCourseNotEnd(CourseEntity courseEntity) {
        String state="未结课";
        UpdateWrapper<CourseEntity> wrapper=new UpdateWrapper<>();
        wrapper.eq("course_code",courseEntity.getCourseCode());
        wrapper.set("course_state",state);
        return courseDao.update(courseEntity,wrapper);
    }

}