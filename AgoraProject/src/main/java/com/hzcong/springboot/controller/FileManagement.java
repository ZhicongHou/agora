package com.hzcong.springboot.controller;


import com.hzcong.data.entities.UserEntity;
import com.hzcong.data.jsonmsg.Message;
import com.hzcong.springboot.service.FileService;
import com.hzcong.springboot.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@Controller
public class FileManagement {

    @Autowired
    private FileService fileService;
    @Autowired
    private SectionService sectionService;


    @ResponseBody
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public Message upload(@RequestParam("file") MultipartFile file, @RequestParam("secId") String secId) {
        return fileService.uploadFile(secId,file);
    }

    @ResponseBody
    @RequestMapping(value = "/getFileList", method = RequestMethod.POST)
    public List<String> getFileList(@RequestParam("secId") String secId) {
        return fileService.getFileList(secId);
    }

    @RequestMapping(value="/downloadFile",method= RequestMethod.GET) //匹配的是href中的download请求
    public ResponseEntity<byte[]> download(@RequestParam("secId") String secId, @RequestParam("fileName") String fileName) throws IOException {
        return fileService.downloadFile(secId,fileName);
    }


    @ResponseBody
    @RequestMapping(value = "/deleteFile")
    public Message deleteFile(@RequestParam("secId")String secId, @RequestParam("fileName")String fileName,
                              HttpSession session){

        UserEntity user = (UserEntity) session.getAttribute("user");
        if(user==null){
            return new Message("1","删除失败，请先登录！");
        }
        return fileService.deleteFile(secId,fileName);
    }

    @ResponseBody
    @RequestMapping(value = "/uploadTeacherImage", method = RequestMethod.POST)
    public Message uploadTeacherImage(@RequestParam("file") MultipartFile file, @RequestParam("type")String type,
                                      HttpSession session) {
        UserEntity user = (UserEntity)session.getAttribute("user");
        if(user==null) {
            return new Message("1","上传失败！");
        }

        fileService.uploadTeacherImage(user.getUserName(),file,type);
        return new Message("0","上传成功！");
    }

    @RequestMapping(value="/downloadTeacherImage",method= RequestMethod.GET) //匹配的是href中的download请求
    public ResponseEntity<byte[]> downloadTeacherImage(@RequestParam("teaName") String teaName, @RequestParam("type") String type) throws IOException {
        return fileService.downloadTeacherImage(teaName,type);
    }

    @ResponseBody
    @RequestMapping(value = "/uploadSectionImage", method = RequestMethod.POST)
    public Message uploadSectionImage(@RequestParam("file") MultipartFile file,
                                      HttpSession session) {
        UserEntity user = (UserEntity)session.getAttribute("user");
        if(user==null) {
            return new Message("1","上传失败！");
        }

        fileService.uploadSectionImage(user.getUserName(),file);
        return new Message("0","上传成功！");
    }

    @RequestMapping(value="/downloadSectionImage",method= RequestMethod.GET) //匹配的是href中的download请求
    public ResponseEntity<byte[]> downloadSectionImage(@RequestParam("secId") String secId,HttpSession session) throws IOException {
        UserEntity user = (UserEntity)session.getAttribute("user");
        return fileService.downloadSectionImage(secId);
    }




//    @RequestMapping(value = "/getTeacherFileList", method = RequestMethod.GET)
//    public String getTeacherFileList(@RequestParam("secId") String secId, Model model) {
//        model.addAttribute("files",fileService.getFileList(secId));
//        SectionEntity section = sectionService.getSectionById(secId);
//        model.addAttribute("section",section);
//        return "teacherRoom::fileList";
//    }
//
//    @RequestMapping(value = "/getStudentFileList", method = RequestMethod.GET)
//    public String getStudentFileList(@RequestParam("secId") String secId, Model model) {
//        model.addAttribute("files",fileService.getFileList(secId));
//        SectionEntity section = sectionService.getSectionById(secId);
//        model.addAttribute("section",section);
//        return "studentRoom::fileList";
//    }

}
