package ljd.classmanager.controller.wx;

import ljd.classmanager.Entity.StudentsEntity;
import ljd.classmanager.Service.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-02-27 13:23
 */
@RestController
@RequestMapping("/wechat")
public class stuInfoController {
    @Autowired
    private StudentsService studentsService;
    @RequestMapping("/getStuInfo")
    public StudentsEntity getStuInfo(@RequestBody StudentsEntity studentsEntitys){
        StudentsEntity studentsEntity=new StudentsEntity();
        if (studentsService.getStuListBySnoAndSname(studentsEntitys).size()==0){
            return null;
        }else {
            studentsEntity = studentsService.getStuListBySnoAndSname(studentsEntitys).get(0);
            studentsEntity.setsOpenid(studentsEntitys.getsOpenid());
            int update = studentsService.updateStuOpenId(studentsEntity);
            return studentsEntity;
        }
    }
}