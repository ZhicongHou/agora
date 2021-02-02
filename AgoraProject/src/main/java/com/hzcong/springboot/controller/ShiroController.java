package com.hzcong.springboot.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ShiroController {

    /**
     * 提示页面的跳转(提示页面需自动跳转到相应的页面)
     * @param type
     * @return
     */
    @RequestMapping("/page/{type}")
    public ModelAndView page(@PathVariable( "type" ) String type, HttpServletRequest request){
       ModelAndView mv = new ModelAndView("errorPage");
       if(type.equals("noAuthentication")){
           mv.addObject("message","haven't logined, please login");
           mv.addObject("redirectUrl","/loginPage");
       }else if(type.equals("noAuthorization")){
           mv.addObject("message","have no permission to access");
           mv.addObject("redirectUrl","/index");
       }
       return mv;
    }
}
