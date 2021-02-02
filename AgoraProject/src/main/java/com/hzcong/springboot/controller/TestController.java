package com.hzcong.springboot.controller;

import com.hzcong.config.SystemConstants;
import com.hzcong.data.entities.RoleEntity;
import com.hzcong.data.entities.UserEntity;
import com.hzcong.springboot.service.RoleService;
import com.hzcong.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
public class TestController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @ResponseBody
    @RequestMapping("roleAdd")
    public Set<RoleEntity> roleAdd(){

//        List<RoleEntity> list = new ArrayList<>();
        RoleEntity teacher = roleService.getRoleByRoleId("c6f6ff356cdb4603892551c427d415b2");
        UserEntity user = userService.getUserByUserName("admin");
        RoleEntity teacherRole = roleService.getRoleByRoleSign(SystemConstants.TEACHER_SIGN);
        user.getRoleSet().add( teacherRole );
        userService.save(user);
        return user.getRoleSet();
//        return list;
    }


}
