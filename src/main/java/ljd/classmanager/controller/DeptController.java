package ljd.classmanager.controller;

import ljd.classmanager.Entity.DeptEntity;
import ljd.classmanager.Service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: classmanager
 * @description: 部门架构控制层
 * @author: liu yan
 * @create: 2019-12-25 16:15
 */
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;
    @RequestMapping("/getdept")
    public List<DeptEntity> getdept(){
        return deptService.getDept();
    }
    @RequestMapping("/addDept")
    public int add(@RequestBody DeptEntity deptEntity){
        return deptService.addDept(deptEntity);
    }
    @RequestMapping("/updateDept")
    public int updateDept(@RequestBody DeptEntity deptEntity){
        System.out.println(deptEntity.toString());
        return deptService.updateDept(deptEntity);
    }
    @RequestMapping("/deleteDept")
    public int deleteDept(@RequestBody DeptEntity deptEntity){
        int deptId=(int)deptEntity.getDeptId();
        return deptService.deletDept(deptId);
    }
    @RequestMapping("/justGetDept")
    public List<DeptEntity> justGetDept(){
        return deptService.justGetDept();
    }
}