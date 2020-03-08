package ljd.classmanager.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ljd.classmanager.Dao.AttendanceDao;
import ljd.classmanager.Entity.AttendanceEntity;
import ljd.classmanager.Service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-03-03 00:10
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceDao attendanceDao;
    @Override
    public Integer addAttendance(AttendanceEntity attendanceEntity) {
        return attendanceDao.insert(attendanceEntity);
    }

    @Override
    public Integer getCountOfSignIn(String attendanceId) {
        QueryWrapper<AttendanceEntity> wrapper=new QueryWrapper<>();
        wrapper.eq("attendance_id",attendanceId);
        wrapper.in("attendance_result","已签到","迟到","旷课","请假","已签退");
        return attendanceDao.selectCount(wrapper);
    }

    @Override
    public Integer signIn(AttendanceEntity attendanceEntity) {
        return attendanceDao.signIn(attendanceEntity);
    }

    @Override
    public List<AttendanceEntity> listHasSign(AttendanceEntity attendanceEntity) {
        return attendanceDao.listHasSign(attendanceEntity);
    }

    @Override
    public Integer updateAttendance(AttendanceEntity attendanceEntity) {
        return attendanceDao.updateAttendance(attendanceEntity);
    }


}