package com.hzcong.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

@Controller
public class PageController {


    @RequestMapping("/manager")
    public String magager() {
        return "manager";
    }

    @RequestMapping("/help")
    public String help() {
        return "help";
    }

    @RequestMapping("/teacher2")
    public String teacher2() {
        return "teacherRoom";
    }


    @ApiIgnore
    @RequestMapping("/QRCode")
    public String QRCode(){
        return "qrcode";
    }

    @ApiIgnore
    @RequestMapping("/resetPassword")
    public String forgetPassword(){
        return "resetPassword";
    }


    @ApiIgnore
    @RequestMapping(value = "register")
    public String register(){
        return "register";
    }


    @ApiIgnore
    @RequestMapping("/component/{htmlName}")
    public String getHtmlCompenent(@PathVariable("htmlName")String htmlName){
        return "component/"+htmlName;
    }



    @ApiIgnore
    @RequestMapping("/login")
    public String login(){
        return "login";
    }


    @ApiIgnore
    @RequestMapping("/teacherAuthentication")
    public String teacherAuthentication(){
        return "teacherAuthentication";
    }

    @ApiIgnore
    @RequestMapping("/userHome")
    public String userHome(){
        return "userHome";
    }


    @ApiIgnore
    @RequestMapping("/myCourse")
    public String giveCourse(){
        return "myCourse";
    }


    @ApiIgnore
    @RequestMapping("/sendMsg")
    public String sendMsg(){
        return "sendMsg(standBy)";
    }

    @ApiIgnore
    @RequestMapping("/alert")
    public String alert(){
        return "alert";
    }

    @RequestMapping("/admin/usersList")
    public String usersList() {
        return "/admin/usersList";
    }

    @RequestMapping("/admin/teachersList")
    public String teachersList() {
        return "/admin/teachersList";
    }

    @RequestMapping("/admin/finishedSectionsList")
    public String finishedSectionsList() {
        return "/admin/finishedSectionsList";
    }

    @RequestMapping("/admin/unfinishedSectionsList")
    public String unfinishedSectionsList() {
        return "/admin/unfinishedSectionsList";
    }

    @RequestMapping("/admin/authenedTeachersList")
    public String authenedTeachersList() {
        return "/admin/authenedTeachersList";
    }

    @RequestMapping("/admin/authoredSectionsList")
    public String authoredSectionsList() {
        return "/admin/authoredSectionsList";
    }

    @RequestMapping("/admin/coursesList")
    public String coursesList() {
        return "/admin/coursesList";
    }

    @RequestMapping("/admin/livingSectionsList")
    public String livingSectionsList() {
        return "/admin/livingSectionsList";
    }

    @RequestMapping("/admin/forbiddenSectionsList")
    public String forbiddenSectionsList() {
        return "/admin/forbiddenSectionsList";
    }

    @RequestMapping("/admin/sendMessageAdmin")
    public String sendMessageAdmin() {
        return "/admin/sendMessageAdmin";
    }

    @RequestMapping("/admin/historyAdmin")
    public String historyAdmin() {
        return "/admin/historyAdmin";
    }

    @RequestMapping("/errorPage/{msg}")
    public ModelAndView errorPage(@PathVariable("msg") String msg){

        ModelAndView mv = new ModelAndView("errorPage");
        if(msg.equals("LoginFilterRefused")){
            mv.addObject("errorMsg","请先登录！");
        }else if (msg.equals("TeacherFilterRefused")){
            mv.addObject("errorMsg","您暂未享有此功能的使用权，请先申请老师认证！");
            mv.addObject("redirectUrl","/teacherAuthentication");
        }else if (msg.equals("RoomFilterRefused")){
            mv.addObject("errorMsg","您没有权限进入该直播间！");
        }else if (msg.equals("AdminFilterRefused")){
            mv.addObject("errorMsg","您没有权限进入管理员页面！");
            mv.addObject("redirectUrl","/admin/enterAdmin");
        }else if(msg.equals("NoSection")){
            mv.addObject("errorMsg","不存在该课程！");
        }else if(msg.equals("RoomProhibited")){
            mv.addObject("errorMsg","该课程已被禁播！");
        }
        return mv;
    }


}
