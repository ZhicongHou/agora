package com.hzcong.springboot.controller;


import com.hzcong.config.SystemConstants;
import com.hzcong.data.entities.CourseEntity;
import com.hzcong.data.entities.SectionEntity;
import com.hzcong.data.entities.TaskEntity;
import com.hzcong.data.entities.UserEntity;
import com.hzcong.data.jsonmsg.Message;
import com.hzcong.data.model.Section;
import com.hzcong.springboot.service.*;
import com.hzcong.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

@Controller
public class SectionController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;


    @ResponseBody
    @RequestMapping(value = "/updateInClassBySecId",method = RequestMethod.POST)
    public Message updateInClassBySecId(String secId, boolean inClass){
        if(sectionService.updateInClassBySecId(secId, inClass)){
            return new Message("0","该门课程正在上课");
        }else{
            return new Message("1","修改失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/isPermit",method = RequestMethod.GET)
    public Message isPermit(String secId){
        if(!sectionService.getSectionById(secId).isProhibited()){
            return new Message("0","可以进入课程");
        }else{
            return new Message("1","该课程已经禁止上课");
        }
    }

    /**
     * 获取所有的课程
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllCourses",method = RequestMethod.GET)
    public Iterable<CourseEntity> getAllCourse() {
        return  courseService.getAllCourses();
    }

    /**
     * 获得所有课程的课表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllSections",method = RequestMethod.GET)
    public Iterable<SectionEntity> getAllSection() {
        return  sectionService.getAllSections();
    }

    @ResponseBody
    @RequestMapping(value = "/getNoPurchase",method = RequestMethod.GET)
    public ArrayList<SectionEntity> getNoPurchase(HttpSession session){
        UserEntity user = (UserEntity) session.getAttribute("user");
        ArrayList<SectionEntity> result = new ArrayList<SectionEntity>();
        Iterable<SectionEntity> sections = sectionService.getAllSections();
        Iterable<TaskEntity> tasks = taskService.getTasksByStuId(user.getId());
        for (SectionEntity section:sections) {
            boolean flag = true;
            for (TaskEntity task:tasks) {
                if(section.getSecId().equals(task.getSecId()) || !section.isAuthorized() || !section.isPurchased()){
                    flag = false;
                    continue;
                }
            }
            if(flag)result.add(section);
        }
        return result;
    }

    /**
     * 获取所有选课的信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllTasks",method = RequestMethod.GET)
    public Iterable<TaskEntity> getAllTasks() {
        return taskService.getAllTasks();
    }

    /**
     * 获取学生选课信息
     * @param id
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getStudentTasks",method = RequestMethod.GET)
    public Iterable<TaskEntity> getStudentTasks(@RequestParam(required = false)String id, HttpSession session) {
        if(id != null){
            return taskService.getTasksByStuId(id);
        }
        return taskService.getTasksByStuId(((UserEntity) session.getAttribute("user")).getId());
    }
    /**
     * 获取某个老师的授课信息
     * @param userId
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTeacherSections")
    public Iterable<SectionEntity> getTeacherSections(@RequestParam(required = false)String userId, HttpSession session){
        if(userId != null){
            return sectionService.getTeacherSectionsByTeaId(userId);
        }
        UserEntity user = (UserEntity)session.getAttribute("user");
        return sectionService.getTeacherSectionsByTeaId(user.getId());
    }

    /**
     * 获取某个课堂的学生信息
     * @param secId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSectionStudent",method = RequestMethod.GET)
    public Iterable<TaskEntity> getSectionStudent(@RequestParam String secId){
        return  taskService.getTasksBySecId(secId);
    }

    /**
     * 添加课表课程
     * @param section
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addSection",method = RequestMethod.POST)
    public Message addSection(SectionEntity section, HttpSession session){
        UserEntity user = (UserEntity)session.getAttribute("user");
        SectionEntity trySection = sectionService.getAppliedSectionByTeaId(user.getId());
        if(trySection!=null && section.getSecId()!=null && !trySection.getSecId().equals(section.getSecId())){
            return new Message("1","您有课程正在审核当中，请在该课程审核结束后才开设新课程！");
        }

        if(section.getSecId()==null){
            section.setSecId(Util.createRandom32Chars());
        }
        section.setTeaId(((UserEntity)session.getAttribute("user")).getId());
        String judgement = "";
        String[] classTime = section.getClassTime().split(",");
        Arrays.sort(classTime,new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if(!SystemConstants.map.get(o1.charAt(2)).equals(SystemConstants.map.get(o2.charAt(2)))){
                    return SystemConstants.map.get(o1.charAt(2))- SystemConstants.map.get(o2.charAt(2));
                }else{
                    String temp1 = o1.substring(3).trim();
                    String temp2 = o2.substring(3).trim();
                    return temp1.compareTo(temp2);
                }
            }
        });
        String realTime = "";
        for(int i=0;i<classTime.length;i++){
            realTime += classTime[i].trim();
            if(i!=classTime.length-1)realTime+=",";
            switch (classTime[i].charAt(2)){
                case '一':judgement += "1,";break;
                case '二':judgement += "2,";break;
                case '三':judgement += "3,";break;
                case '四':judgement += "4,";break;
                case '五':judgement += "5,";break;
                case '六':judgement += "6,";break;
                case '日':judgement += "7,";break;
                default:System.out.println("classTime解析 出错!");break;
            }
        }
        section.setTeaName(((UserEntity)session.getAttribute("user")).getUserName());
        section.setRoomNumber(Util.createRandom32Chars());
        section.setJudgement(judgement);
        section.setClassTime(realTime);
        String secId = Util.createRandom32Chars();
        section.setSecId(secId);
        sectionService.addSection(section);
        fileService.renameSectionImage(user.getUserName(),secId);
        return new Message("0","提交成功，请您耐心等候……");
    }


    @RequestMapping("/giveCourse")
    public ModelAndView giveCourse(@RequestParam(defaultValue = "")String secId){
        ModelAndView mv = new ModelAndView("giveCourse");
        Iterable<CourseEntity> list = courseService.getAllCourses();
        mv.addObject("courseList",list);
        if(!secId.equals("")){
            SectionEntity section = sectionService.getSectionById(secId);
            mv.addObject("section",section);
        }
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/updatePaid",method = RequestMethod.POST)
    public Message updatePaid(String userId){
        if(sectionService.updatePaidBySecId(userId, true)){
            return new Message("0","已经支付");
        }else{
            return new Message("1","支付修改失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/deleteSection",method = RequestMethod.POST)
    public Message deleteSection(String secId){
        if(sectionService.deleteSection(secId)){
            return new Message("0","拒绝成功");
        }else{
            return new Message("1","拒绝失败");
        }
    }

    @RequestMapping(value = "/getSection",method = RequestMethod.GET)
    public ModelAndView getSection(@RequestParam("secId") String secId, HttpSession session){
        ModelAndView mv = new ModelAndView("courseInfo");
        SectionEntity section = sectionService.getSectionById(secId);
        if(section==null){
            mv.addObject("3","无此课程");
            return mv;
        }
        mv.addObject("section",section);
        UserEntity user = (UserEntity)session.getAttribute("user");
        if (section.isProhibited()){
            mv.addObject("sectionStat","prohibited");
        }else if(section.getAttended()==section.getFrequency()){
            mv.addObject("sectionStat","finished");
        } else if(user!=null && taskService.getTaskByStuIdAndSecId(user.getId(),section.getSecId())!=null){
            mv.addObject("sectionStat","student");
        }else if(user!=null && section.getTeaName().equals(user.getUserName())){
            mv.addObject("sectionStat","teacher");
        }else if(section.isPurchased()){
            mv.addObject("sectionStat", "canBuy");
        }else{
            mv.addObject("sectionStat","notbuy");
        }
        return mv;
    }


    @RequestMapping("/buyWin")
    public ModelAndView buyWin(@RequestParam String secId){

        SectionEntity section = sectionService.getSectionById(secId);
        return new ModelAndView("buyWin","section",section);
    }






    /**
     * 根据课程的编号获取课程的视频流uid
     * @param session
     * @param secId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUid",method = RequestMethod.GET)
    public Section getUid(HttpSession session, @RequestParam String secId){
        UserEntity user = (UserEntity) session.getAttribute("user");
        SectionEntity sectionEntity = sectionService.getSectionById(secId);
        UserEntity teacher = userService.getUserByUserId(sectionEntity.getTeaId());
        Section section = new Section(teacher.getUid(), user.getUid(),String.valueOf(secId));
        return section;
    }

    /**
     * 获取登陆用户的用户名
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUsername")
    public String getUsername(HttpSession session){
        return ((UserEntity) session.getAttribute("user")).getUserName();
    }

    /**
     * 获取所有的学生
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAllStudents")
    public Iterable<UserEntity>  getAllStudents(){
        return userService.getUsersByRoleName("student");
    }

    /**
     * 获取所有的老师
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAllTeachers")
    public Iterable<UserEntity>  getAllTeachers(){
        return userService.getUsersByRoleName("teacher");
    }

    /**
     * 根据课程的编号获取该课程的所有学生对象
     * @param secId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getStudentsBySecId")
    public Iterable<UserEntity>  getStudentsBySecId(@RequestParam String secId){
        return userService.getUsersBySecId(secId);
    }

    /**
     * 管理员admin批准老师的授课申请
     * @param secId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/admin/authorizeSection",method = RequestMethod.POST)
    public Message authorizeSection(@RequestParam String secId, @RequestParam double proportion){
    /*
    修改authorized为1
     */
        if(proportion<0||proportion>100){
            return new Message("1","输入的百分比不正确");
        }else {
            if(sectionService.updateProportionBySecId(secId, proportion)){
                sectionService.authorizeSection(secId);
                sectionService.updatePurchased(secId, true);
                return new Message("0","工资百分比修改成功");
            }else{
                return new Message("1","工资百分比修改失败");
            }
        }
    }


}
