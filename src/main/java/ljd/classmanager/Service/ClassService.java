package ljd.classmanager.Service;

import ljd.classmanager.Entity.ClassEntity;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-01-04 16:53
 */
public interface ClassService {
     List<ClassEntity> getClassList(ClassEntity classEntity);
     List<ClassEntity> getClasstoSel(ClassEntity classEntity);
     Integer getTotal(ClassEntity classEntity);
     Integer addClass(ClassEntity classEntity);
     Integer UpdateClass(ClassEntity classEntity);
     Integer DeleteClass(String ClassCode);
}
