package ljd.classmanager.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ljd.classmanager.Entity.DeptEntity;

import java.util.List;

/**
 * @program: classmanager
 * @description: 部门架构Dao类
 * @author: liu yan
 * @create: 2019-12-25 16:01
 */
public interface DeptDao extends BaseMapper<DeptEntity> {
     List<DeptEntity> getDept();
}