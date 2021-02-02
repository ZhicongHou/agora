package com.hzcong.springboot.controller;

import com.hzcong.config.SystemConstants;
import com.hzcong.data.entities.*;
import com.hzcong.springboot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 进入各个页面时候初始化操作
 */
@Controller
public class EneterPageController {
    @Autowired
    private SectionService sectionService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private TeaAuthenService teaAuthenService;
    @Autowired
    private CourseService courseService;


    /**
     * 学生进入房间初始化操作
     * @param secId
     * @param model
     * @return
     */
    @RequestMapping(value = "/studentRoom", method = RequestMethod.GET)
    public String enterStudentRoom(@RequestParam("secId") String secId, Model model){
        model.addAttribute("appId", SystemConstants.Agora_AppId);
        SectionEntity section = sectionService.getSectionById(secId);
        model.addAttribute("section",section);
        return "studentRoom";
    }

    @RequestMapping(value = "/teacherRoom", method = RequestMethod.GET)
    public String enterTeacherRoom(@RequestParam("secId") String secId, Model model){
        model.addAttribute("appId", SystemConstants.Agora_AppId);
        SectionEntity section = sectionService.getSectionById(secId);
        model.addAttribute("section",section);
        Iterable<TaskEntity> students = taskService.getTasksBySecId(secId);
        model.addAttribute("students",students);
        return "teacherRoom";
    }

    /**
     * 加载index初始化界面
     * @return
     */
    @RequestMapping("/index")
    public String index(@RequestParam(defaultValue = "1") int pageNum, Model model) {
        int pageSize=8;
        Page<SectionEntity> sections = sectionService.getAllAuthorizedSectionOfPage(pageNum-1,pageSize);
        model.addAttribute("sections",sections);
        model.addAttribute("total",sections.getTotalElements());
        return "index";
    }

    /**
     * 加载index初始化界面
     * @return
     */
    @RequestMapping("/indexOtherPage")
    public String indexOtherPage(@RequestParam(defaultValue = "1") int pageNum,Model model) {
        int pageSize=8;
        Page<SectionEntity> sections = sectionService.getAllAuthorizedSectionOfPage(pageNum-1,pageSize);
        model.addAttribute("sections",sections);
        model.addAttribute("total",sections.getTotalElements());
        return "index::sectionPage";
    }

    /**
     * 刷新房间的学生列表
     * @param model
     * @param secId
     * @return
     */
    @RequestMapping(value = "/refeshFileList",method = RequestMethod.GET)
    public String test(Model model, @RequestParam String secId){
        String dirPath = "F:\\download" + File.separator + secId;
        File[] files = new File(dirPath).listFiles();
        if(files==null){
            File storeDir = new File(dirPath);
            storeDir.mkdir();
        }else{
            List<String> fileList = new ArrayList<>();
            for (File file : files) {
                fileList.add(file.getName());
            }
            model.addAttribute("files",fileList);
        }
        SectionEntity section = sectionService.getSectionById(secId);
        model.addAttribute("section",section);
        return "teacherRoom::fileList";
    }

    /**
     * 进入我的课程时候初始化
     * @param pageNum
     * @param model
     * @return
     */
    @RequestMapping(value = "/myCourse",method = RequestMethod.GET)
    public String getMycourseMessage(@RequestParam(defaultValue = "1") int pageNum, Model model, HttpSession session){

        UserEntity user = (UserEntity)session.getAttribute("user");
        int pageSize=8;
        Page<SectionEntity> sections =sectionService.getTeacherAuthorizedSectionOfPage(user.getId(),pageNum-1,pageSize);
        Page<TaskEntity> tasks = taskService.getStudentTaskOfPage(user.getId(),pageNum-1,pageSize);
        SectionEntity appliedSection = sectionService.getAppliedSectionByTeaId(user.getId());
        model.addAttribute("sections",sections);
        model.addAttribute("tasks",tasks);
        model.addAttribute("appliedSection",appliedSection);
        model.addAttribute("userName",user.getUserName());
        return "myCourse";
    }

    /**
     * 分页读取我的课程的课程数据
     * @param pageNum
     * @param model
     * @return
     */
    @RequestMapping(value = "/getOtherSectionOfPage",method = RequestMethod.GET)
    public String getOtherSectionOfPage(@RequestParam(defaultValue = "1") int pageNum, Model model, HttpSession session){
        UserEntity user = (UserEntity)session.getAttribute("user");
        model.addAttribute("userName",user.getUserName());
        int pageSize=8;
        Page<SectionEntity> sections =sectionService.getTeacherAuthorizedSectionOfPage(user.getId(),pageNum-1,pageSize);
        model.addAttribute("sections",sections);
        return "myCourse::myClass";
    }

    /**
     * 分页读取我的课程的开课数据
     * @param pageNum
     * @param model
     * @return
     */
    @RequestMapping(value = "/getOtherTaskOfPage",method = RequestMethod.GET)
    public String getOtherTaskOfPage(HttpSession session,@RequestParam(defaultValue = "1") int pageNum, Model model){
        UserEntity user = (UserEntity)session.getAttribute("user");
        int pageSize=8;
        Page<TaskEntity> tasks = taskService.getStudentTaskOfPage(user.getId(),pageNum-1,pageSize);
        List<TaskEntity> tasks2 = tasks.getContent();
        model.addAttribute("userName",user.getUserName());
        model.addAttribute("tasks",tasks);
        return "myCourse::purchasedClasses";
    }

    /**
     * 用户分页读取用户详细信息
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
        return "/userInfo";
    }

    /**
     * 用户分页读取老师详细信息
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
        String imageUrl = "/downloadTeacherImage?"+"teaName="+teaName+"&type=";
        model.addAttribute("person",imageUrl+"person");
        return "/teacherInfo";
    }

    /**
     * 用户读取已授权课程详细信息
     * @param secId
     * @return
     */
//    @RequestMapping(value = "/getSectionInfo",method = RequestMethod.GET)
//    public String getSectionInfo(@RequestParam(defaultValue = "") String secId, Model model) {
//        SectionEntity section = sectionService.getSectionById(secId);
//        Iterable<TaskEntity> tasks = taskService.getTasksBySecId(secId);
//        CourseEntity course = courseService.getCourseById(section.getCourId());
//        model.addAttribute("section",section);
//        model.addAttribute("tasks",tasks);
//        model.addAttribute("course",course);
//        return "/courseInfo";
//    }
    @RequestMapping(value = "/getSectionInfo",method = RequestMethod.GET)
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


}
