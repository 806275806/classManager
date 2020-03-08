package ljd.classmanager.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ljd.classmanager.Entity.ClassEntity;
import ljd.classmanager.Entity.DeptEntity;
import ljd.classmanager.Entity.MajorEntity;
import ljd.classmanager.Entity.StudentsEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-01-15 16:54
 */
public interface StudentsDao extends BaseMapper<StudentsEntity> {
    List<StudentsEntity> getStuList(StudentsEntity studentsEntity);
    List<StudentsEntity> getStuListBySnoAndSname(StudentsEntity studentsEntity);
    Integer getStuCount(StudentsEntity studentsEntity);
    List<DeptEntity> getDeptByName(String name);
    List<MajorEntity> getMajorByName(String name);
    List<ClassEntity> getClassByName(String name);
    List<StudentsEntity> getStuByOpenid(@Param("openid") String openId);
}
