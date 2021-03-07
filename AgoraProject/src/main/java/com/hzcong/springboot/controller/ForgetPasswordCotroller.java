package com.hzcong.springboot.controller;
import com.google.gson.Gson;
import com.hzcong.data.entities.UserEntity;
import com.hzcong.data.jsonmsg.Message;
import com.hzcong.springboot.service.VerifyService;
import com.hzcong.util.MailUtils;
import com.hzcong.springboot.service.UserService;
import com.hzcong.util.PasswordHelper;
import com.hzcong.util.Util;
import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Controller
public class    ForgetPasswordCotroller {

    @Autowired
    private UserService userService;

    @Autowired
    private VerifyService verifyService;

    @ResponseBody
    @RequestMapping("/generateForgetMail")
    public Message generateForgetMail(@RequestParam("emailAccount")String emailAccount,
                                      @RequestParam("code")String code){
        UserEntity user = userService.getUserByEmail(emailAccount);

        if (!verifyService.checkVerifyCode(code)){
            return new Message("1","验证码错误");
        }

        if(user==null){
            return new Message("1","无此用户");
        }

        String sign = Util.createRandom32Chars();
        user.setChangingPw(true);
        user.setChangingPwSign(sign);
        userService.save(user);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        long nowTime = calendar.getTimeInMillis();
        System.out.println("当前时间：" + sdf.format(nowTime));
        calendar.add(Calendar.HOUR,+2);
        long validTime = calendar.getTimeInMillis();
        System.out.println("两小时后时间时间：" + sdf.format(validTime));

        Map<String,String> map = new HashMap<>();
        map.put("emailNumber",emailAccount);
        map.put("sign",sign);
        map.put("validTime",""+validTime);
        String parameterString = new Gson().toJson(map);
        String encodeString = Base64.encodeToString(parameterString.getBytes());

//        String receiver = "1171366087@qq.com"; //后面应该改成emailAccount
//        String receiver = "ZhicongHou@163.com"; //后面应该改成emailAccount
        boolean result = new MailUtils().sendChangePasswordMail(emailAccount,encodeString);
        System.out.println("发送结果："+result);

        if(result){
            return new Message("0","发送成功");
        }else{
            return new Message("1","发送失败");
        }
    }


    @RequestMapping("/changePassword")
    public ModelAndView changePassword(@RequestParam( "encodeString" ) String encodeString){
        ModelAndView mv = new ModelAndView();
        String decodeString = Base64.decodeToString(encodeString);
        Map<String,String> parameters = new Gson().fromJson(decodeString,Map.class);
        String emailNumber = parameters.get("emailNumber");
        String sign = parameters.get("sign");
        long validTime = Long.valueOf(parameters.get("validTime"));
        long nowTime = Calendar.getInstance().getTimeInMillis();
        UserEntity user = userService.getUserByEmail(emailNumber);
        if(user==null ||!user.isChangingPw() || !user.getChangingPwSign().equals(sign) || nowTime>validTime){
            mv.setViewName("errorPage");
            mv.addObject("message","链接已失效");
            mv.addObject("redirectUrl","/agora/index");
        }else{
            mv.setViewName("setNewPassword");
            mv.addObject("encodeString",encodeString);
        }
        return mv;
    }


    @ResponseBody
    @RequestMapping(value = "/executeChangePassword",method = RequestMethod.POST)
    public Message executeChangePassword(@RequestParam( "encodeString" ) String encodeString,
                                              @RequestParam("newPassword")String newPassword){
        String decodeString = Base64.decodeToString(encodeString);
        Map<String,String> parameters = new Gson().fromJson(decodeString,Map.class);
        String emailNumber = parameters.get("emailNumber");
        String sign = parameters.get("sign");
        long validTime = Long.valueOf(parameters.get("validTime"));
        long nowTime = Calendar.getInstance().getTimeInMillis();
        UserEntity user = userService.getUserByEmail(emailNumber);
        if(user==null ||!user.isChangingPw() || !user.getChangingPwSign().equals(sign) || nowTime>validTime){
            return new Message("1","链接已失效");
        }else{
//            userService.delete(user.getUserName());
            user.setPassword(PasswordHelper.encryptPassword(user.getUserName(),newPassword));
            user.setChangingPw(false);
            userService.save(user);
            return new Message("0","修改成功");
        }
    }

}
