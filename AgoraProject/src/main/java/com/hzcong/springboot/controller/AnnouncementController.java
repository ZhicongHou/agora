package com.hzcong.springboot.controller;

import com.hzcong.data.entities.AnnouncementEntity;
import com.hzcong.data.jsonmsg.Message;
import com.hzcong.springboot.service.AnnouncementService;
import com.hzcong.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @ResponseBody
    @RequestMapping(value = "/getAnnouncementBySecId",method = RequestMethod.POST)
    public AnnouncementEntity getAnnouncementBySecId(@RequestParam String secId){
        return  announcementService.getAnnouncementBySecId(secId);
    }


    @ResponseBody
    @RequestMapping(value = "/writeAnnouncementBySecId",method = RequestMethod.POST)
    public Message writeAnnouncementBySecId(@RequestParam String secId, @RequestParam String content,
                                            @RequestParam String title, @RequestParam String publishTime){
        AnnouncementEntity announcement = new AnnouncementEntity();
        announcement.setId(Util.createRandom32Chars());
        announcement.setSecId(secId);
        announcement.setTitle(title);
        announcement.setContent(content);
        announcement.setPublishTime(publishTime);
        announcementService.deleteAnnouncementBySecId(secId);
        boolean result = announcementService.writeAnnouncementBySecId(announcement);
        if(result){
            return new Message("0","发布成功！");
        }else{
            return new Message("1","发布失败！");
        }
    }

    @RequestMapping(value = "/refreshTeacherAnnouncement",method = RequestMethod.GET)
    public String refreshTeacherAnnouncementBysecId(@RequestParam String secId, Model model){
        AnnouncementEntity announcement = announcementService.getAnnouncementBySecId(secId);
        model.addAttribute("announcement",announcement);
        System.out.println(announcement.getContent());
        return "teacherRoom::announcementId";
    }

    @RequestMapping(value = "/refreshStudentAnnouncement",method = RequestMethod.GET)
    public String refreshStudentAnnouncementBysecId(@RequestParam String secId, Model model){
        AnnouncementEntity announcement = announcementService.getAnnouncementBySecId(secId);
        model.addAttribute("announcement",announcement);
        System.out.println(announcement.getContent());
        return "studentRoom::announcementId";
    }

}
