package ljd.classmanager.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ljd.classmanager.Entity.ClassEntity;

import java.util.List;

/**
 * @program: classmanager
 * @description: 班级实体类
 * @author: liu yan
 * @create: 2020-01-04 16:52
 */
public interface ClassDao extends BaseMapper<ClassEntity> {
     List<ClassEntity> getClassList(ClassEntity classEntity);
     List<ClassEntity> getClasstoSel(ClassEntity classEntity);
     Integer getTotal(ClassEntity classEntity);
}
