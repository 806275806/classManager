package ljd.classmanager.controller;

import ljd.classmanager.Entity.ClassEntity;
import ljd.classmanager.Page.PageHelper;
import ljd.classmanager.Service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-01-04 16:55
 */
@RestController
public class ClassController {
    @Autowired
    private ClassService classService;
    @RequestMapping("/getClass")
    public PageHelper<ClassEntity> getClassList(ClassEntity classEntity){
        PageHelper<ClassEntity> classEP=new PageHelper<ClassEntity>();
        Integer total=classService.getTotal(classEntity) ;
        classEP.setTotal(total);
        List<ClassEntity> list=classService.getClassList(classEntity);
        classEP.setRows(list);
        return classEP;
    }
    @RequestMapping("/getClasstoSel")
    public List<ClassEntity> getClasstoSel(@RequestBody ClassEntity classEntity){
        return classService.getClasstoSel(classEntity);
    }
    @RequestMapping("/addClass")
    public Integer addClass(@RequestBody ClassEntity classEntity){
        return classService.addClass(classEntity);
    }
    @RequestMapping("/updateClass")
    public Integer updateClass(@RequestBody ClassEntity classEntity){
        return classService.UpdateClass(classEntity);
    }
    @RequestMapping("/deleteClass")
    public Integer deleteClass(@RequestBody ClassEntity classEntity){
        return classService.DeleteClass(classEntity.getClassCode());
    }
}