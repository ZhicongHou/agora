package com.hzcong.springboot.controller;

import com.hzcong.config.SystemConstants;
import com.hzcong.data.entities.*;
import com.hzcong.data.jsonmsg.Message;
import com.hzcong.springboot.service.*;
import com.hzcong.util.Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 分页读取管理者所需要的信息及操作(待用)
 */
//@Controller
//@RequestMapping("/admin")
public class OldManagerController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private TeaAuthenService teaAuthenService;

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/enterAdmin")
    public String enterAdmin() {
        return "adminLogin";
    }

    @RequestMapping(value = "/adminLogin",method = RequestMethod.POST)
    public String adminLogin(@RequestParam(defaultValue = "") String userName, @RequestParam(defaultValue = "") String password,
                             HttpSession session, HttpServletRequest request) {

        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        Subject subject = SecurityUtils.getSubject();
        UserEntity user = userService.getUserByUserName(userName);
        if(user==null || !user.isActived() || user.isProhibited()){
            return "adminLogin";
        }

        try {
            subject.login(token);
            //        System.out.println(subject.hasRole(SystemConstants.ADMIN_SIGN));
            if(subject.hasRole(SystemConstants.ADMIN_SIGN)){
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("subject", subject);
                return "redirect:/admin/admin";
            }else{
                subject.logout();
                return "adminLogin";
            }
        } catch (IncorrectCredentialsException | UnknownAccountException | ExcessiveAttemptsException ice) {
            // 捕获密码错误异常
            subject.logout();
            return "adminLogin";
        }
    }


    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            subject.logout();
        }
        if(request.getSession().getAttribute("user")!=null){
            request.getSession().removeAttribute("user");
        }
        return "adminLogin";
    }


    /**
     * 进入管理者
     * @return
     */
    @RequestMapping(value = "/admin")
    public String admin(@RequestParam(defaultValue = "1") int pageNum, Model model,HttpSession session) {
        UserEntity user=null;
        user = (UserEntity) session.getAttribute("user");
        if(user==null){
            return "adminLogin";
        }else if(!(user.getUserName().equals("admin"))){
            return "adminLogin";
        }else {
            int pageSize=10;
            Page<UserEntity> users = userService.getAllUsersOfPage(pageNum-1,pageSize);
            model.addAttribute("users",users);
            model.addAttribute("sTotal",users.getTotalElements());

            Page<TeaAuthenticationEntity> teachers =teaAuthenService.getAllAuthenedTeacherOfPage(pageNum-1,pageSize);
            model.addAttribute("teachers",teachers);
            model.addAttribute("tTotal",teachers.getTotalElements());

            Page<SectionEntity> finishedSections = sectionService.getFinishedSectionOfPage(pageNum-1,pageSize);
            model.addAttribute("finishedSections",finishedSections);
            model.addAttribute("fSTotal",finishedSections.getTotalElements());

            Page<SectionEntity> unfinishedSections = sectionService.getUnfinishedSectionOfPage(pageNum-1,pageSize);
            model.addAttribute("unfinishedSections",unfinishedSections);
            model.addAttribute("ufSTotal",unfinishedSections.getTotalElements());

            Page<TeaAuthenticationEntity> unAuthenTeahers = teaAuthenService.getAllUnauthenedTeacherOfPage(pageNum-1,pageSize);
            model.addAttribute("unAuthenTeahers",unAuthenTeahers);
            model.addAttribute("uATotal",unAuthenTeahers.getTotalElements());

            Page<SectionEntity> unAuthorSections = sectionService.getUnAuthorSectionOfPage(pageNum-1,pageSize);
            model.addAttribute("unAuthorSections",unAuthorSections);
            model.addAttribute("uASTotal",unAuthorSections.getTotalElements());

            Page<CourseEntity> courses = courseService.getCoursesOfPage(pageNum-1,pageSize);
            model.addAttribute("courses",courses);
            model.addAttribute("csTotal",courses.getTotalElements());

            Page<SectionEntity> inClassSction = sectionService.getInClassSctionOfPage(pageNum-1,pageSize);
            model.addAttribute("inClassSction",inClassSction);
            model.addAttribute("icsTotal",inClassSction.getTotalElements());

            Page<SectionEntity> prohibitSction = sectionService.getProhibitSctionOfPage(pageNum-1,pageSize);
            model.addAttribute("prohibitSction",prohibitSction);
            model.addAttribute("psTotal",prohibitSction.getTotalElements());

            model.addAttribute("appId", SystemConstants.Agora_AppId);

            return "admin";
        }
    }

    /**
     * 获取用户列表
     * @param pageNum
     * @param model
     * @return
     */
    @RequestMapping("/getUserOfPage")
    public String getUserOfPage(@RequestParam(defaultValue = "1") int pageNum, Model model) {
        int pageSize=10;
        Page<UserEntity> users = userService.getAllUsersOfPage(pageNum-1,pageSize);
        model.addAttribute("users",users);
        model.addAttribute("sTotal",users.getTotalElements());
        return "admin::tStudent";
    }

    /**
     * 获取已认证的老师列表
     * @param pageNum
     * @param model
     * @return
     */
    @RequestMapping("/getTeacherOfPage")
    public String getTeacherOfPage(@RequestParam(defaultValue = "1") int pageNum, Model model) {
        int pageSize=10;
        Page<TeaAuthenticationEntity> teachers =teaAuthenService.getAllAuthenedTeacherOfPage(pageNum-1,pageSize);
        model.addAttribute("teachers",teachers);
        model.addAttribute("tTotal",teachers.getTotalElements());
        return "admin::tTeacher";
    }

    /**
     * 获取已完结课程
     * @param pageNum
     * @param model
     * @return
     */
    @RequestMapping("/getFinishedSectionOfPage")
    public String getFinishedSectionOfPage(@RequestParam(defaultValue = "1") int pageNum, Model model) {
        int pageSize=10;
        Page<SectionEntity> finishedSections = sectionService.getFinishedSectionOfPage(pageNum-1,pageSize);
        model.addAttribute("finishedSections",finishedSections);
        model.addAttribute("fSTotal",finishedSections.getTotalElements());
        return "admin::tFSection";
    }

    /**
     * 获取未完结课程
     * @param pageNum
     * @param model
     * @return
     */
    @RequestMapping("/getUnfinishedSectionOfPage")
    public String getUnfinishedSectionOfPage(@RequestParam(defaultValue = "1") int pageNum, Model model) {
        int pageSize=10;
        Page<SectionEntity> unfinishedSections = sectionService.getUnfinishedSectionOfPage(pageNum-1,pageSize);
        model.addAttribute("unfinishedSections",unfinishedSections);
        model.addAttribute("ufSTotal",unfinishedSections.getTotalElements());
        return "admin::tSection";
    }

    /**
     * 获取未认证老师
     * @param pageNum
     * @param model
     * @return
     */
    @RequestMapping("/getUnauthenTeacherOfPage")
    public String getUnauthenTeacherOfPage(@RequestParam(defaultValue = "1") int pageNum, Model model) {
        int pageSize=10;
        Page<TeaAuthenticationEntity> unAuthenTeahers = teaAuthenService.getAllUnauthenedTeacherOfPage(pageNum-1,pageSize);
        model.addAttribute("unAuthenTeahers",unAuthenTeahers);
        model.addAttribute("uATotal",unAuthenTeahers.getTotalElements());
        return "admin::teaAuthen";
    }

    /**
     * 获取未授权课程
     * @param pageNum
     * @param model
     * @return
     */
    @RequestMapping("/getUnAuthorSectionOfPage")
    public String getCourse (@RequestParam(defaultValue = "1") int pageNum, Model model) {
        int pageSize=10;
        Page<SectionEntity> unAuthorSections = sectionService.getUnAuthorSectionOfPage(pageNum-1,pageSize);
        model.addAttribute("unAuthorSections",unAuthorSections);
        model.addAttribute("uASTotal",unAuthorSections.getTotalElements());
        return "admin::secAuthor";
    }


    /**
     * 获取科目
     * @param pageNum
     * @param model
     * @return
     */
    @RequestMapping("/getAllCoursesOfPage")
    public String getUnAuthorSectionOfPage(@RequestParam(defaultValue = "1") int pageNum, Model model) {
        int pageSize=10;
        Page<CourseEntity> courses = courseService.getCoursesOfPage(pageNum-1,pageSize);
        model.addAttribute("courses",courses);
        model.addAttribute("csTotal",courses.getTotalElements());
        return "admin::tAdd";
    }

    /**
     * 获取正在上课列表
     * @param pageNum
     * @param model
     * @return
     */
    @RequestMapping("/getInClassSction")
    public String getInClassSction(@RequestParam(defaultValue = "1") int pageNum, Model model) {
        int pageSize=10;
        Page<SectionEntity> inClassSction = sectionService.getInClassSctionOfPage(pageNum-1,pageSize);
        model.addAttribute("inClassSction",inClassSction);
        model.addAttribute("icsTotal",inClassSction.getTotalElements());
        return "admin::tliving";
    }

    /**
     * 获取禁播列表
     * @param pageNum
     * @param model
     * @return
     */
    @RequestMapping("/getProhibitSction")
    public String getProhibitSction(@RequestParam(defaultValue = "1") int pageNum, Model model) {
        int pageSize=10;
        Page<SectionEntity> prohibitSction = sectionService.getProhibitSctionOfPage(pageNum-1,pageSize);
        model.addAttribute("prohibitSction",prohibitSction);
        model.addAttribute("psTotal",prohibitSction.getTotalElements());
        return "admin::tForbindden";
    }

    /**
     * 管理者分页读取用户详细信息
     * @param userName
     * @param model
     * @return
     */
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.GET)
    public String getUserInfo(@RequestParam(defaultValue = "") String userName, Model model) {
        UserEntity user = userService.getUserByUserName(userName);
        Iterable<TaskEntity> tasks = taskService.getTasksByStuId(user.getId());
        model.addAttribute("tasks",tasks);
        model.addAttribute("user",user);
        return "userInfo";
    }

    /**
     * 管理者分页读取老师详细信息
     * @param teaName
     * @param model
     * @return
     */
    @RequestMapping(value = "/getTeacherInfo",method = RequestMethod.GET)
    public String getTeacherInfo(@RequestParam(defaultValue = "") String teaName, Model model) {
        UserEntity user = userService.getUserByUserName(teaName);
        TeaAuthenticationEntity teaAuthen = teaAuthenService.getTeaAuthenByUserId(user.getId());
        Iterable<SectionEntity> sections = sectionService.getTeacherSectionsByTeaId(user.getId());
        model.addAttribute("user",user);
        model.addAttribute("teaAuten",teaAuthen);
        model.addAttribute("sections",sections);
        return "teacherInfo";
    }


    /**
     * 管理者分页读取老师详细信息
     * @param teaName
     * @param model
     * @return
     */
    @RequestMapping(value = "/getTeacherInfoForAdmin",method = RequestMethod.GET)
    public String getTeacherInfoForAdmin(@RequestParam(defaultValue = "") String teaName, String type, Model model) {
        UserEntity user = userService.getUserByUserName(teaName);
        TeaAuthenticationEntity teaAuthen = teaAuthenService.getTeaAuthenByUserId(user.getId());
        Iterable<SectionEntity> sections = sectionService.getTeacherSectionsByTeaId(user.getId());
        model.addAttribute("user",user);
        model.addAttribute("teaAuten",teaAuthen);
        model.addAttribute("sections",sections);
        String imageUrl = "/downloadTeacherImage?"+"teaName="+teaName+"&type=";
        model.addAttribute("front",imageUrl+"front");
        model.addAttribute("back",imageUrl+"back");
        model.addAttribute("person",imageUrl+"person");
        return "teacherInfoForAdmin";
    }




    /**
     * 管理者读取已授权课程详细信息
     * @param secId
     * @param model
     * @return
     */
    @RequestMapping(value = "/getSectionInfo",method = RequestMethod.GET)
    public String getSectionInfo(@RequestParam(defaultValue = "") String secId, Model model) {
        SectionEntity section = sectionService.getSectionById(secId);
        Iterable<TaskEntity> tasks = taskService.getTasksBySecId(secId);
        CourseEntity course = courseService.getCourseById(section.getCourId());
        model.addAttribute("section",section);
        model.addAttribute("tasks",tasks);
        model.addAttribute("course",course);
        return "courseInfoToAdmin";
    }

    @ResponseBody
    @RequestMapping(value = "/updateProhibitedBySecId",method = RequestMethod.POST)
    public Message updateProhibitedBySecId(String secId, boolean prohibited){
        if(sectionService.updateProhibitedBySecId(secId, prohibited)){
            if(prohibited==false)
                return new Message("0","启用这门课成功！");
            else
                return new Message("0","禁止这门课成功！");
        }else{
            if(prohibited==false)
                return new Message("1","禁止这门课成功！");
            else
                return new Message("1","禁止这门课失败！");
        }
    }

    /**
     * 更改工资比例
     * @param secId
     * @param proportion
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateProportionBySecId",method = RequestMethod.POST)
    public Message updateProportionBySecId(String secId, double proportion){
        if(proportion<0||proportion>100){
            return new Message("1","输入的百分比不正确");
        }
        if(sectionService.updateProportionBySecId(secId, proportion)){
            SectionEntity sectionEntity = sectionService.getSectionById(secId);
            sectionService.updateActualAmountBySecId(secId,sectionEntity.getCurAmount()*sectionEntity.getPrice()*sectionEntity.getProportion()/100.0);
            return new Message("0","工资百分比修改成功");
        }else{
            return new Message("1","工资百分比修改失败");
        }
    }


    /**
     * 添加一门课程
     * @param courseTitle
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addCourse",method = RequestMethod.POST)
    public Message addCourse(@RequestParam("courseTitle")String courseTitle){
        if(courseTitle == null || courseTitle.equals(""))return new Message("0","传入的科目名不能为空");
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setId(Util.createRandom32Chars());
        courseEntity.setTitle(courseTitle);
        if(courseService.addCourse(courseEntity)){
            return new Message("0","添加成功");
        }else{
            return new Message("1","添加数据库失败");
        }
    }

    /**
     * 删除一节课程
     * @param courseId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteCourse",method = RequestMethod.GET)
    public Message deleteCourse(@RequestParam("courseId") String courseId){
        if(courseId == null || courseId.equals(""))return new Message("1","传入的id不能为空");
        if(((List<SectionEntity>)sectionService.getSectionEntitiesByCourId(courseId)).size()!=0){
            return new Message("1","当前科目存在section，不能删除");
        }
        if(courseService.deleteCourseById(courseId)){
            return  new Message("0","删除成功");
        }else{
            return  new Message("1","删除失败");
        }
    }












}
