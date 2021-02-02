package com.hzcong.springboot.controller;

import com.hzcong.config.SystemConstants;
import com.hzcong.data.entities.UserEntity;
import com.hzcong.data.jsonmsg.Message;
import com.hzcong.springboot.service.TeaAuthenService;
import com.hzcong.springboot.service.VerifyService;
import com.hzcong.util.MailUtils;
import com.hzcong.springboot.service.UserService;
import com.hzcong.util.PasswordHelper;
import com.hzcong.util.Util;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

@Controller
public class
 UserController {

    @Autowired
    private UserService userService;
//    @Autowired
//    public VerifyService verifyCodeService;

    @Autowired
    private MailUtils mailUtils;

    @Autowired
    private VerifyService verifyService;

//    @Autowired
//    private RoleService roleService;

    @Autowired
    private TeaAuthenService teaAuthenService;

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Message login(@RequestParam("userName") String userName, @RequestParam("password") String password,
                                     @RequestParam("code") String code, HttpServletRequest request) {
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        Subject subject = SecurityUtils.getSubject();
        UserEntity user = userService.getUserByUserName(userName);
        if (!verifyService.checkVerifyCode(code)){
            return new Message("1","验证码错误");
        }
        else if(user==null || !user.isActived() || user.isProhibited()){
            return new Message("1","用户不存在或未激活、或者已经被禁用");
        }
        try {
            subject.login(token);
            request.getSession().setAttribute("user",user);
            request.getSession().setAttribute("subject",subject);
            return new Message("0","登录成功");
        } catch (IncorrectCredentialsException ice) {
            // 捕获密码错误异常
            return new Message("1","密码错误");
        } catch (UnknownAccountException uae) {
            // 捕获未知用户名异常
            return new Message("1","用户名不存在");
        } catch (ExcessiveAttemptsException eae) {
            // 捕获错误登录过多的异常
            return new Message("1","出现未知错误");
        }
    }



    @ResponseBody
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Message register(@RequestParam("userName") String userName, @RequestParam("password") String password,
                                 @RequestParam("realName") String realName, @RequestParam("sex") String sex,
                                 @RequestParam("emailAccount") String emailAccount, @RequestParam("code") String code) {
        if(!verifyService.checkVerifyCode(code)){
            return new Message("1","验证码错误");
        }
        else if(userService.getUserByUserName(userName)!=null){
            return new Message("1","用户名已存在");
        }else if( emailAccount!= SystemConstants.DEFAULT_EMAIL && userService.getUserByEmail(emailAccount)!=null) {
            return new Message("1","邮箱已注册");
        }else{
            String newPassword = PasswordHelper.encryptPassword(userName,password);
            UserEntity user = new UserEntity(emailAccount,userName,newPassword,realName,false,sex);
            user.setId(Util.createRandom32Chars());
            user.setCreateTime(new Timestamp(System.currentTimeMillis()));
            String userId = Base64.encodeToString(user.getId().getBytes());
            boolean result = mailUtils.sendRegisterMail(user.getEmail(), userId);
            if(result){
                userService.save(user);
                return new Message("0","注册确认链接已发邮件至:"+emailAccount+"，请到邮箱确认注册");
            }else{
                return new Message("1","邮件发送失败");
            }
        }
    }

    @RequestMapping(value = "/activate")
    public ModelAndView activate(@RequestParam("encodeString")String encodeString){
        ModelAndView modelAndView = new ModelAndView("errorPage");
        String userId = Base64.decodeToString(encodeString);
        UserEntity user = userService.getUserByUserId(userId);
        if(user != null){
            if(user.isActived()){
                modelAndView.addObject("errorMsg","链接失效");
            }else{
                userService.updateActived(true, userId);
                modelAndView.addObject("errorMsg","激活成功");
            }
        } else{
            modelAndView.addObject("errorMsg","激活失败，用户不存在");
        }
        modelAndView.addObject("redirectUrl", "/index");
        return modelAndView;
    }

    @RequestMapping(value = "/prohibit")
    public ModelAndView prohibit(@RequestParam("encodeString")String encodeString){
        ModelAndView modelAndView = new ModelAndView("errorPage");
        String userId = Base64.decodeToString(encodeString);
        UserEntity user = userService.getUserByUserId(userId);
        if(user != null){
            if(!user.isProhibited()){
                modelAndView.addObject("errorMsg","该用户没有被禁用");
            }else{
                userService.updateProhibited(false, user.getId());
                modelAndView.addObject("errorMsg","用户禁用解除");
            }
        } else{
            modelAndView.addObject("errorMsg","用户不存在");
        }
        modelAndView.addObject("redirectUrl", "/index");
        return modelAndView;
    }

    /**
     * 更新一个对象的信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public Message updateUser(@RequestParam String userName, @RequestParam String realName,@RequestParam String sex, HttpSession session) {
        UserEntity user = null;
        if (userName == null) {
            return new Message("false", "修改失败");
        } else {
            user = userService.getUserByUserName(userName);
            if (user == null) {
                return new Message("false", "账户不存在");
            }
        }
        user.setRealName(realName);
        user.setSex(sex);
        userService.save(user);
        session.removeAttribute("user");
        session.setAttribute("user", user);
        return new Message("true", "修改成功");
    }


    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Message logout(HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            subject.logout();
        }
        if(request.getSession().getAttribute("user")!=null){
            request.getSession().removeAttribute("user");
        }
        return new Message("0","退出成功");
    }


    @ApiOperation(value = "删除用户", notes = "根据用户的userName来删除用户")
    @ApiImplicitParam(name = "id", value = "用户email", required = true, dataType = "String", paramType = "param")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String deleteUser(@RequestParam String username, HttpSession session) {
        userService.deleteByUserName(username);
        return "success";
    }

    @ResponseBody
    @ApiOperation(value = "获取学生用户列表", notes = "获取学生用户列表")
    @RequestMapping(value = "/getAllStudents", method = RequestMethod.GET)
    public Iterable<UserEntity> getAllStudents() {
        return userService.getUsersByRoleName("student");
    }

    @ResponseBody
    @ApiOperation(value = "获取老师用户列表", notes = "获取老师用户列表")
    @RequestMapping(value = "/getAllTeachers", method = RequestMethod.GET)
    public Iterable<UserEntity> getAllTeachers() {
        return userService.getUsersByRoleName("teacher");
    }


    @ApiOperation(value = "获取用户详细信息", notes = "根据用户名来获取用户详细信息")
    @ApiImplicitParam(name = "email", value = "用户email", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/getUser", method = RequestMethod.PUT)
    public UserEntity getUser(@RequestParam String userName) {
        return userService.getUserByUserName(userName);
    }

    @RequestMapping(value = "clearSession")
    public String clearSession(HttpSession session) {
        session.invalidate();
        return  "testLogin";
    }


}
