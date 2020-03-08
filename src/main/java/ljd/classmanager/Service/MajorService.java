package ljd.classmanager.Service;

import ljd.classmanager.Entity.MajorEntity;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2019-12-31 17:45
 */
public interface MajorService {
    public int addMajor(MajorEntity majorEntity);
    public int updateMajor(MajorEntity majorEntity);
    public int deleteMajor(String majorCode);
    List<MajorEntity> getMajor(Integer deptId);
}
