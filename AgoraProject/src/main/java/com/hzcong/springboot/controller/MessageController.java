package com.hzcong.springboot.controller;

import com.hzcong.config.SystemConstants;
import com.hzcong.data.entities.MessageEntity;
import com.hzcong.data.entities.UserEntity;
import com.hzcong.data.jsonmsg.Message;
import com.hzcong.data.jsonmsg.MessageMsg;
import com.hzcong.springboot.service.MessageService;
import com.hzcong.springboot.service.UserService;
import com.hzcong.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    private static final int PAGESIZE = 3;


    @ResponseBody
    @RequestMapping(value = "/getMessagesByRecieverId",method = RequestMethod.POST)
    public Iterable<MessageEntity> getMessagesByRecieverId(@RequestParam String recieveId){
        return  messageService.getMessagesByRecieverId(recieveId);
    }

    @ResponseBody
    @RequestMapping(value = "/getMessageBySendId",method = RequestMethod.POST)
    public Iterable<MessageEntity> getMessageBySendId(@RequestParam String sendId){
        return messageService.getMessgesBySenderId(sendId);
    }

    @ResponseBody
    @RequestMapping(value = "/getUnreadedMessagesByRecieverId",method = RequestMethod.POST)
    public Iterable<MessageEntity> getUnreadedMessagesByRecieverId(@RequestParam String recieveId){
        return  messageService.getUnreadedMessagesByRecieverId(recieveId);
    }

    @RequestMapping("/history")
    public ModelAndView history(@RequestParam(value = "pageNum",defaultValue = "1")int pageNum, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        String recieveId = user.getId();//登陆用户的id
        Page<MessageEntity> messages =  messageService.getMessagesByRecieverId(recieveId,pageNum-1,PAGESIZE);
        return new ModelAndView("history","totalElements",messages.getTotalElements());
    }



    @ResponseBody
    @RequestMapping(value = "/getRecMessages", method = RequestMethod.GET)
    public List<MessageEntity> getRecMessages(@RequestParam("pageNum") int pageNum, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        String recieveId = user.getId();//登陆用户的id
        List<MessageEntity> messages = messageService.getMessagesByRecieverId(recieveId,pageNum-1, SystemConstants.PAGESIZEOFMESSAGE).getContent();
        for(int i=0;i<messages.size();i++)messageService.updateReaded(true, messages.get(i).getReceiverId());
        return messages;
    }


//    @ResponseBody
//    @RequestMapping(value = "/getMessageBySendId", method = RequestMethod.GET)
//    public Iterable<MessageEntity> getMessageBySendId(@RequestParam String sendId) {
//        return messageService.getMessgesBySenderId(sendId);
//    }

    @ResponseBody
    @RequestMapping(value = "/addMessage", method = RequestMethod.POST)
    public Message addMessage(@RequestParam("receiver")String receiver,@RequestParam("receiverId") String receiverId, @RequestParam("content") String content, HttpSession session) {
        System.out.println(receiver+"   "+receiverId+"  "+content);
        Message message = new Message("0", "添加消息成功");
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setSenderId("d2e227be541f4085b9275e2f8085178e");  //默认是管理者发送，目前也只有管理者能发送
        messageEntity.setSenderName("admin");//现为admin才能发送消息，后面应该改成对应的登陆用户
        messageEntity.setContent(content);
//      默认为admin
        System.out.println(session.getAttribute("user"));
        UserEntity user = (UserEntity) session.getAttribute("user");
        System.out.println(user.getUserName());
        ArrayList<UserEntity> users = null;
        if (receiver.equals("teachers")) {
            users = (ArrayList<UserEntity>) userService.getUsersByRoleName("teacher");
        } else if (receiver.equals("all")) {
            users = (ArrayList<UserEntity>) userService.getAll();
        }
        if(users!=null){
            for (UserEntity temp : users) {
                if(temp.getUserName().equals(user.getUserName()))continue;
                messageEntity.setId(Util.createRandom32Chars());
                messageEntity.setReceiverId(temp.getId());
                messageEntity.setReceiverName(temp.getUserName());
                if (!messageService.addMessage(messageEntity)) {
                    message.setFlagAndContent("1", "添加消息失败");
                }
            }
        }else if(receiver.equals("one")){
            UserEntity receivedUser = userService.getUserByUserName(receiverId);
//            UserEntity user = (UserEntity) session.getAttribute("user");//现在默认是管理员，以后换成用户
            if (receivedUser == null) {
                message.setFlagAndContent("1", "没有这个用户");
            } else {
                messageEntity.setId(Util.createRandom32Chars());
                messageEntity.setReceiverId(receivedUser.getId());
                messageEntity.setReceiverName(receivedUser.getUserName());
                System.out.println(messageEntity);
                if (!messageService.addMessage(messageEntity)) {
                    message.setFlagAndContent("1", "添加消息失败");
                }
            }
        }else{
            message.setFlagAndContent("1", "type类型错误");
        }
        return message;
    }


    @ResponseBody
    @RequestMapping(value = "/getRecMessageCount",method = RequestMethod.GET)
    public MessageMsg getRecMessageCount(HttpSession session){
        int count = messageService.getMessageCountByRecieverId(((UserEntity)session.getAttribute("user")).getId());
        return new MessageMsg(SystemConstants.PAGESIZEOFMESSAGE, count);
    }

}
