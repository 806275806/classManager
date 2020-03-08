package ljd.classmanager.controller;


import ljd.classmanager.Entity.StudentsEntity;
import ljd.classmanager.Page.PageHelper;
import ljd.classmanager.Service.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-01-15 17:01
 */
@RestController
public class StudentsController {
    @Autowired
    private StudentsService studentsService;
    @RequestMapping("/getStuList")
    public PageHelper<StudentsEntity> getStuList(StudentsEntity studentsEntity){
        PageHelper<StudentsEntity> pagehelper=new PageHelper<>();
        pagehelper.setRows(studentsService.getStuList(studentsEntity));
        pagehelper.setTotal(studentsService.getStuCount(studentsEntity));
        return pagehelper;
    }
    @RequestMapping("/addStudents")
    public Integer addStudents(@RequestBody StudentsEntity studentsEntity){
        return studentsService.addStudents(studentsEntity);
    }
    @RequestMapping("/updateStudents")
    public Integer updateStudents(@RequestBody StudentsEntity studentsEntity){
        return studentsService.updateStudents(studentsEntity);
    }
    @RequestMapping("/delStudents")
    public Integer delStudents(@RequestBody StudentsEntity studentsEntity){
        return studentsService.delStudents(studentsEntity);
    }
    /**
     * excel导入数据
     */
    @PostMapping("/importexcel")
    @Transactional(rollbackFor = Exception.class)
    public Object importWatchExcel(@RequestParam("excelFile") MultipartFile xlsFile) {
        return studentsService.importExcel(xlsFile);
    }
}