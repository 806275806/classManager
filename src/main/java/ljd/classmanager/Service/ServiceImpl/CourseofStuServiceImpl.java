package ljd.classmanager.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ljd.classmanager.Dao.CourseofStuDao;
import ljd.classmanager.Entity.CourseEntity;
import ljd.classmanager.Entity.CourseOfStuEntity;
import ljd.classmanager.Service.CourseofStuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-02-20 22:38
 */
@Service
@Transactional
public class CourseofStuServiceImpl implements CourseofStuService {
    @Autowired
    private CourseofStuDao courseofStuDao;
    @Override
    public int addStuToCourse(CourseOfStuEntity courseOfStuEntity) {
        return courseofStuDao.insert(courseOfStuEntity);
    }

    @Override
    public int delStuinCourse(String courseCode, String classCode) {
        QueryWrapper<CourseOfStuEntity> wrapper=new QueryWrapper<>();
        wrapper.eq("course_code",courseCode);
        wrapper.eq("class_code",classCode);
        return courseofStuDao.delete(wrapper);
    }

    @Override
    public Integer getAttendanceStuCount(CourseOfStuEntity courseOfStuEntity) {
        return courseofStuDao.getAttendanceStuCount(courseOfStuEntity);
    }

    @Override
    public List<CourseOfStuEntity> getAttendnceStuToHistory(CourseOfStuEntity courseOfStuEntity) {
        return courseofStuDao.getAttendnceStuToHistory(courseOfStuEntity);
    }


    @Override
    public List<CourseOfStuEntity> getStuOfCourse(CourseEntity courseEntity) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("course_code",courseEntity.getCourseCode());
        wrapper.eq("class_nickname",courseEntity.getClassnickName());
        return courseofStuDao.selectList(wrapper);
    }


    @Override
    public List<CourseOfStuEntity> getAttendnceStu(String courseCode, String classNickname, String attendanceId,String attendanceResult,String sNo) {
        return courseofStuDao.getAttendnceStu(courseCode,classNickname,attendanceId,attendanceResult,sNo);
    }

    @Override
    public List<CourseOfStuEntity> getCourseStu(String courseCode, String classNickname) {
        return courseofStuDao.getCourseStu(courseCode,classNickname);
    }


}