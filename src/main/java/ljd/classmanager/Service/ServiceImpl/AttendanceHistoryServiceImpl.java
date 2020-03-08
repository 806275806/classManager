package ljd.classmanager.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import ljd.classmanager.Dao.AttendanceHistoryDao;
import ljd.classmanager.Entity.AttendanceHistoryEntity;
import ljd.classmanager.Service.AttendanceHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-03-02 00:25
 */
@Service
public class AttendanceHistoryServiceImpl implements AttendanceHistoryService {
    @Autowired
    private AttendanceHistoryDao attendanceHistoryDao;
    @Override
    public List<AttendanceHistoryEntity> getAttendanceHistory(AttendanceHistoryEntity attendanceHistoryEntity) {
        return attendanceHistoryDao.getAttendanceHistory(attendanceHistoryEntity);
    }

    @Override
    public List<AttendanceHistoryEntity> getHistory(AttendanceHistoryEntity attendanceHistoryEntity) {
        return attendanceHistoryDao.getHistory(attendanceHistoryEntity);
    }

    @Override
    public int addAttendanceHistory(AttendanceHistoryEntity attendanceHistoryEntity) {
        return attendanceHistoryDao.insert(attendanceHistoryEntity);
    }

    @Override
    public int updateAttendance(AttendanceHistoryEntity attendanceHistoryEntity) {
        UpdateWrapper<AttendanceHistoryEntity> wrapper=new UpdateWrapper<>();
        wrapper.eq("attendance_id",attendanceHistoryEntity.getAttendanceId());
        wrapper.set("attendance_state",attendanceHistoryEntity.getAttendanceState());
        return attendanceHistoryDao.update(attendanceHistoryEntity,wrapper);
    }

    @Override
    public int delAttendance(AttendanceHistoryEntity attendanceHistoryEntity) {
        QueryWrapper<AttendanceHistoryEntity> wrapper=new QueryWrapper<>();
        wrapper.eq("attendance_id",attendanceHistoryEntity.getAttendanceId());
        return attendanceHistoryDao.delete(wrapper);
    }

    @Override
    public Integer getHistoryCount(AttendanceHistoryEntity attendanceHistoryEntity) {
        return attendanceHistoryDao.getHistoryCount(attendanceHistoryEntity);
    }

    @Override
    public Integer updateState(AttendanceHistoryEntity attendanceHistoryEntity) {
        return attendanceHistoryDao.updateState(attendanceHistoryEntity);
    }

    @Override
    public List<AttendanceHistoryEntity> getAttendanceTime(String attendanceId) {
        QueryWrapper<AttendanceHistoryEntity> wrapper=new QueryWrapper<>();
        wrapper.eq("attendance_id",attendanceId);
        return attendanceHistoryDao.selectList(wrapper);
    }
}