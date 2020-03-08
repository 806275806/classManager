package ljd.classmanager.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ljd.classmanager.Entity.AttendanceHistoryEntity;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-03-02 00:23
 */
public interface AttendanceHistoryDao extends BaseMapper<AttendanceHistoryEntity> {
    List<AttendanceHistoryEntity> getAttendanceHistory(AttendanceHistoryEntity attendanceHistoryEntity);
    List<AttendanceHistoryEntity> getHistory(AttendanceHistoryEntity attendanceHistoryEntity);
    Integer getHistoryCount(AttendanceHistoryEntity attendanceHistoryEntity);
    int addAttendanceHistory(AttendanceHistoryEntity attendanceHistoryEntity);
    Integer updateState(AttendanceHistoryEntity attendanceHistoryEntity);
}
