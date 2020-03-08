package ljd.classmanager.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ljd.classmanager.Entity.AttendanceEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-03-01 13:55
 */
public interface AttendanceDao extends BaseMapper<AttendanceEntity> {
    Integer addAttendance(AttendanceEntity attendanceEntity);
    Integer signIn(AttendanceEntity attendanceEntity);
    List<AttendanceEntity> listHasSign(AttendanceEntity attendanceEntity);
    Integer updateAttendance(AttendanceEntity attendanceEntity);
}
