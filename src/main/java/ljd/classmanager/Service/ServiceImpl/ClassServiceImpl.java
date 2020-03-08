package ljd.classmanager.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import ljd.classmanager.Dao.ClassDao;
import ljd.classmanager.Entity.ClassEntity;
import ljd.classmanager.Service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-01-04 16:54
 */
@Service
@Transactional
public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassDao classDao;
    @Override
    public List<ClassEntity> getClassList(ClassEntity classEntity) {
        return classDao.getClassList(classEntity);
    }

    @Override
    public List<ClassEntity> getClasstoSel(ClassEntity classEntity) {
        return classDao.getClasstoSel(classEntity);
    }

    @Override
    public Integer getTotal(ClassEntity classEntity) {
        return classDao.getTotal(classEntity);
    }


    @Override
    public Integer addClass(ClassEntity classEntity) {
        return classDao.insert(classEntity);
    }

    @Override
    public Integer UpdateClass(ClassEntity classEntity) {
        UpdateWrapper<ClassEntity> wrapper=new UpdateWrapper<>();
        wrapper.eq("class_code",classEntity.getId());
        return classDao.update(classEntity,wrapper);
    }

    @Override
    public Integer DeleteClass(String ClassCode) {
        QueryWrapper<ClassEntity> wrapper=new QueryWrapper<>();
        wrapper.eq("class_code",ClassCode);
        return classDao.delete(wrapper);
    }
}