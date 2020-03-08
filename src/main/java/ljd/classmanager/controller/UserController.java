package ljd.classmanager.controller;

import ljd.classmanager.Entity.RoleEntity;
import ljd.classmanager.Entity.UserEntity;
import ljd.classmanager.Page.PageHelper;
import ljd.classmanager.Service.RoleService;
import ljd.classmanager.Service.UserService;
import ljd.classmanager.config.setPasswordMD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * @program: classmanager
 * @description:
 * @author: liu yan
 * @create: 2020-01-11 16:03
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @RequestMapping("/getUserList")
    public PageHelper<UserEntity> getUserList(UserEntity userEntity){
        PageHelper<UserEntity> pageHelper=new PageHelper<>();
        pageHelper.setRows(userService.getUserList(userEntity));
        pageHelper.setTotal(userService.getUserTotal(userEntity));
        System.out.println(userEntity);
        return pageHelper;
    }
    @RequestMapping("/addUser")
    @Transactional
    public Integer addUser(@RequestBody UserEntity userEntity){
        String str=userEntity.getHaveRole();
       setPasswordMD5 setPd=new setPasswordMD5();
       String pd=setPd.setMD5(userEntity.getUserCode(),userEntity.getUserTel());//初始密码默认为联系方式
        userEntity.setUserPassword(pd);
        String[] strs=str.split(",");
        if (userService.addUser(userEntity)==1){
            for (int i=0;i<strs.length;i++){
                int roleId=Integer.valueOf(strs[i]);
                userEntity.setHaveRoleId(roleId);
                System.out.println(userEntity);
                roleService.addRole(userEntity);
            }
        }
        return 0;
//        return userService.addUser(userEntity);
    }
    @RequestMapping("/updateUser")
    @Transactional
    public Integer updateUser(@RequestBody UserEntity userEntity){
        String str=userEntity.getHaveRole();
        ArrayList<Integer> toRoles=new ArrayList<>();//前端传过来的角色集合
        ArrayList<Integer> dbRoles=new ArrayList<>();//数据库获取已有的角色集合
        ArrayList<Integer> outRoles=new ArrayList<>();//数据库原有角色比修改后的角色多余的角色集合
        ArrayList<Integer> inRoles=new ArrayList<>();//数据库原有角色比修改后的角色缺少的角色集合
        String[] toUpdateRoles=str.split(",");//需要操作的角色集合
        for (int i=0;i<toUpdateRoles.length;i++){
            toRoles.add(Integer.valueOf(toUpdateRoles[i]));
        }
        List<RoleEntity> list= roleService.getRoleByUserCode(userEntity.getUserCode());
        for (int j=0;j<list.size();j++){
            dbRoles.add(list.get(j).getRoleId());
        }
        for (Integer i:dbRoles){   //1
            if (!toRoles.contains(i)){
                outRoles.add(i);
            }
        }
        for (Integer i:toRoles){   //1,2
            if (!dbRoles.contains(i)){
                inRoles.add(i);
            }
        }
        for (int i=0;i<inRoles.size();i++){
            userEntity.setHaveRoleId(inRoles.get(i));//给实体类设置角色Id
            roleService.addRole(userEntity);//新增用户所属多余角色（补全更改后的角色集合）
        }
        for (int i=0;i<outRoles.size();i++){
            roleService.delUser_Role(userEntity.getUserCode(),outRoles.get(i));//删除用户所属多余角色（只保留更改后的角色集合）
        }
        return userService.updateUser(userEntity);
    }
    @RequestMapping("/delUser")
    @Transactional
    public Integer delUser(@RequestBody UserEntity userEntity){
        roleService.delUser_Role(userEntity.getUserCode(),0);
        return userService.delUser(userEntity);
    }
}