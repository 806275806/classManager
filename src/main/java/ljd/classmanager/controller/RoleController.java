package ljd.classmanager.controller;

import ljd.classmanager.Entity.RoleEntity;
import ljd.classmanager.Service.RoleService;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-01-11 22:16
 */
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;
    @RequestMapping("/getRoleList")
    public List<RoleEntity> getRoleList(){
     return roleService.getRoleList();
    }
//    @RequestMapping("/a")
////    public List<RoleEntity> geta(String userCode,Integer deptId){
////        return roleService.getRoleByUserCode(userCode,deptId);
////    }
    }