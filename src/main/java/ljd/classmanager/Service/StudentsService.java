package ljd.classmanager.Service;

import ljd.classmanager.Entity.ClassEntity;
import ljd.classmanager.Entity.DeptEntity;
import ljd.classmanager.Entity.MajorEntity;
import ljd.classmanager.Entity.StudentsEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-01-15 16:59
 */
public interface StudentsService {
    List<StudentsEntity> getStuList(StudentsEntity studentsEntity);
    List<StudentsEntity> getStuListBySnoAndSname(StudentsEntity studentsEntity);
    List<StudentsEntity> getStuByClassCode(String ClassCode);
    Integer getStuCount(StudentsEntity studentsEntity);
    Integer addStudents(StudentsEntity studentsEntity);
    Integer updateStudents(StudentsEntity studentsEntity);
    Integer updateStuOpenId(StudentsEntity studentsEntity);
    Integer delStudents(StudentsEntity studentsEntity);
    Map<String, Object> importExcel(MultipartFile xlsFile);
    List<StudentsEntity> getStuByOpenid(String openId);
    List<DeptEntity> getDeptByName(String name);
    List<MajorEntity> getMajorByName(String name);
    List<ClassEntity> getClassByName(String name);
}
