package ljd.classmanager.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import ljd.classmanager.Dao.DeptDao;
import ljd.classmanager.Entity.DeptEntity;
import ljd.classmanager.Service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2019-12-25 16:08
 */
@Service
@Transactional
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao deptDao;
    @Override
    public List<DeptEntity> getDept() {
        return deptDao.getDept();
    }

    @Override
    public List<DeptEntity> justGetDept() {
        QueryWrapper<DeptEntity> wrapper=new QueryWrapper<>();
        return deptDao.selectList(wrapper);
    }

    @Override
    public int addDept(DeptEntity deptEntity) {
        return deptDao.insert(deptEntity);
    }

    @Override
    public int updateDept(DeptEntity deptEntity) {
        UpdateWrapper wrapper=new UpdateWrapper();
        wrapper.eq("dept_id",deptEntity.getDeptId());
        wrapper.set("dept_name",deptEntity.getDeptName());
        return deptDao.update(deptEntity,wrapper);
    }

    @Override
    public int deletDept(Integer deptId) {
        QueryWrapper<DeptEntity> wrapper=new QueryWrapper<>();
        wrapper.eq("dept_id",deptId);
        return deptDao.delete(wrapper);
    }

    @Override
    public List<DeptEntity> getDeptByName(String name) {
        QueryWrapper<DeptEntity> wrapper=new QueryWrapper<>();
        wrapper.eq("dept_name",name);
        return deptDao.selectList(wrapper);
    }
}