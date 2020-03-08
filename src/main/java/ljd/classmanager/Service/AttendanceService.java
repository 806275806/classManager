package ljd.classmanager.Service;

import ljd.classmanager.Entity.AttendanceEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-03-03 00:08
 */
public interface AttendanceService {
    Integer addAttendance(AttendanceEntity attendanceEntity);
    Integer getCountOfSignIn(String attendanceId);
    Integer signIn(AttendanceEntity attendanceEntity);
    List<AttendanceEntity> listHasSign(AttendanceEntity attendanceEntity);
    Integer updateAttendance(AttendanceEntity attendanceEntity);
}
