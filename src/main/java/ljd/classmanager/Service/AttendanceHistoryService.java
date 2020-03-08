package ljd.classmanager.Service;

import ljd.classmanager.Entity.AttendanceHistoryEntity;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-03-02 00:24
 */
public interface AttendanceHistoryService {
    List<AttendanceHistoryEntity> getAttendanceHistory(AttendanceHistoryEntity attendanceHistoryEntity);
    List<AttendanceHistoryEntity> getHistory(AttendanceHistoryEntity attendanceHistoryEntity);
    int addAttendanceHistory(AttendanceHistoryEntity attendanceHistoryEntity);
    int updateAttendance(AttendanceHistoryEntity attendanceHistoryEntity);
    int delAttendance(AttendanceHistoryEntity attendanceHistoryEntity);
    Integer getHistoryCount(AttendanceHistoryEntity attendanceHistoryEntity);
    Integer updateState(AttendanceHistoryEntity attendanceHistoryEntity);
    List<AttendanceHistoryEntity> getAttendanceTime(String attendanceId);
}
