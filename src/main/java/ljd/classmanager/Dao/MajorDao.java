package ljd.classmanager.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ljd.classmanager.Entity.MajorEntity;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2019-12-30 15:54
 */
public interface MajorDao extends BaseMapper<MajorEntity> {
    List<MajorEntity> getMajor(Integer deptId);
}
