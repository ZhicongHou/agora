package com.hzcong.springboot.controller;

import com.hzcong.config.SystemConstants;
import com.hzcong.data.entities.TeaAuthenticationEntity;
import com.hzcong.data.entities.UserEntity;
import com.hzcong.data.jsonmsg.Message;
import com.hzcong.springboot.service.RoleService;
import com.hzcong.springboot.service.TeaAuthenService;
import com.hzcong.springboot.service.UserService;
import com.hzcong.util.Util;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TeaAuthenController {

    @Autowired
    private TeaAuthenService teaAuthenService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @ResponseBody
    @RequestMapping(value = "/addTeaAuthen",method = RequestMethod.POST)
    public Message addTeaAuthen(TeaAuthenticationEntity teaAuthentication, HttpSession session){
        UserEntity user = (UserEntity) session.getAttribute("user");
        TeaAuthenticationEntity temp = teaAuthenService.getTeaAuthenByUserId(user.getId());
        if(temp!=null){
            if(temp.isAuthorized()){
                return new Message("1","您已获得教师身份，无需重复申请！");
            }else{
                teaAuthenService.deleteAuthenByUserId(temp.getUserId());
            }
        }
        teaAuthentication.setId(Util.createRandom32Chars());
        teaAuthentication.setUserId(user.getId());
        teaAuthentication.setUserName(user.getUserName());
        teaAuthenService.addTeaAuthen(teaAuthentication);
        return new Message("0","提交成功，正在审核中，请您耐心等待……");
    }


    @ResponseBody
    @RequestMapping(value = "/admin/authorizeTeaAuthen",method = RequestMethod.POST)
    public Message authorizeTeaAuthen(@RequestParam("userId")String userId){
        UserEntity teaUser = userService.getUserByUserId(userId);
        if(teaAuthenService.updateAuthorized(userId, true)){
            teaUser.getRoleSet().add(roleService.getRoleByRoleSign(SystemConstants.TEACHER_SIGN));
            userService.save(teaUser);
            return new Message("0","认证成功！");
        }else{
            return new Message("1","认证失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAuthen",method = RequestMethod.POST)
    public Message deleteAuthen(@RequestParam("userId") String userId){
        if(teaAuthenService.deleteAuthenByUserId(userId)){
            return new Message("0","拒绝成功");
        }else{
            return new Message("1","拒绝失败");
        }
    }
}
