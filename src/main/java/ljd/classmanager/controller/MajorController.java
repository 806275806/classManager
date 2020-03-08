package ljd.classmanager.controller;

import ljd.classmanager.Entity.MajorEntity;
import ljd.classmanager.Service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2019-12-31 17:49
 */
@RestController
public class MajorController {
    @Autowired
    private MajorService majorService;
    @RequestMapping("/addMajor")
    public int addMajor(@RequestBody MajorEntity majorEntity){
        return majorService.addMajor(majorEntity);
    }
    @RequestMapping("/updateMajor")
    public int updateMajor(@RequestBody MajorEntity majorEntity){
        return majorService.updateMajor(majorEntity);
    }
    @RequestMapping("/deleteMajor")
    public int deleteMajor(@RequestBody MajorEntity majorEntity){
        return majorService.deleteMajor(majorEntity.getMajorCode());
    }
    @RequestMapping("/getMajor")
    public List<MajorEntity> getMajor(Integer deptId){
        return majorService.getMajor(deptId);
    }
}