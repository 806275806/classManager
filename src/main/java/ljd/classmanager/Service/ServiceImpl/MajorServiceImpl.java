package ljd.classmanager.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import ljd.classmanager.Dao.MajorDao;
import ljd.classmanager.Entity.MajorEntity;
import ljd.classmanager.Service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2019-12-31 17:46
 */
@Service
@Transactional
public class MajorServiceImpl implements MajorService {
    @Autowired
    private MajorDao majorDao;
    @Override
    public int addMajor(MajorEntity majorEntity) {
        return majorDao.insert(majorEntity);
    }

    @Override
    public int updateMajor(MajorEntity majorEntity) {
        UpdateWrapper<MajorEntity> wrapper=new UpdateWrapper<>();
        wrapper.eq("major_code",majorEntity.getMajorCode());
        return majorDao.update(majorEntity,wrapper);
    }
    @Override
    public int deleteMajor(String majorCode) {
        QueryWrapper<MajorEntity> wrapper=new QueryWrapper<>();
        wrapper.eq("major_code",majorCode);
        return majorDao.delete(wrapper);
    }

    @Override
    public List<MajorEntity> getMajor(Integer deptId) {
        return majorDao.getMajor(deptId);
    }

}