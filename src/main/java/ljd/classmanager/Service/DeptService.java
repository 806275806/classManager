package ljd.classmanager.Service;

import ljd.classmanager.Entity.DeptEntity;

import java.util.List;

/**
 * @program: classmanager
 * @description: 部门架构服务类
 * @author: liu yan
 * @create: 2019-12-25 16:07
 */
public interface DeptService {
    public List<DeptEntity> getDept();
    public List<DeptEntity> justGetDept();
    public int addDept(DeptEntity deptEntity);
    public int updateDept(DeptEntity deptEntity);
    public int deletDept(Integer deptId);
    List<DeptEntity> getDeptByName(String name);
}
