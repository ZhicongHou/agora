package com.hzcong.springboot.controller;
//
//import com.google.gson.Gson;
//import com.qzyembryo.data.VO.MessGet;
//import com.qzyembryo.data.VO.MessSend;
//import com.qzyembryo.data.VO.RoomChat;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.websocket.*;
//import javax.websocket.server.ServerEndpoint;
//import java.util.List;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//
///**
// * 一个房间的通信
// */
//
//@ServerEndpoint(value = "/websocket")
//@Controller
//public class ListController {            //每进入一个用户，就新建一个ListController对象
//
//    private static ConcurrentMap<String, RoomChat> roomMap = new ConcurrentHashMap<>();
//    private RoomChat roomChat;
//    @OnOpen
//    public void onOpen(Session session){
//        System.out.println("有新连接加入！");
//    }
//    @OnClose
//    public void onClose(){
//        System.out.println("有一连接关闭！");
//    }
//    @OnError
//    public void onError(Session session, Throwable error){
//        System.out.println("发生错误");
//        error.printStackTrace();
//    }
//
//    /**
//     * 收到客户端消息后调用的方法
//     * @param data 客户端发送过来的消息
//     * @param session 可选的参数
//     */
//    @OnMessage
//    public void onMessage(String data, Session session) {
//        MessGet messGet = new Gson().fromJson(data, MessGet.class);
//        String room = messGet.getRoom();
//        String userName = messGet.getUserName();
//        String status = messGet.getStatus();
//        System.out.printf("来自room:%s的%s的消息：%s\n", room, userName, status);
//
//
//        //通过房间号，将受到的消息分发到对应的房间，然后在房间内自己处理
//        roomChat = roomMap.get(room);
//        if(roomChat==null){
//            roomChat = new RoomChat(room);
//            roomMap.put(room,roomChat);
//        }
//        solve(messGet,session);
//    }
//
//
//    public void solve(MessGet messGet, Session session){
//        String userName = messGet.getUserName();
//        String status = messGet.getStatus();
//        if(status.equals("in")){
//            in(userName,session);
//        }else if(status.equals("out")){
//            out(userName);
//        }else if(status.equals("readList")){
//            readList();
//        }
//        else if(status.equals("apply")){
//            apply(userName);
//        } else if(status.equals("teacherApply")){
//            teacherApply(userName);
//        }else if(status.equals("cancelSence")){
//            cancelSence(userName);
//        }
//    }
//
//    //当老师发消息时，参数userName设置为"teacher"
//    private void in(String userName, Session session){
//        if(userName.equals("teacher")){
//            roomChat.setTeacherSession(session);
//        }else{
//            roomChat.addStudent(userName,session);
//            MessSend messSend = new MessSend(userName, "in", roomChat.getAmount());
//            if(roomChat.getTeacherSession()!=null) sendMessage(roomChat.getTeacherSession(),messSend);
//        }
//    }
//
//    private void out(String userName){
//        if(userName.equals("teacher")){
//            roomChat.setTeacherSession(null);
//        }else{
//            roomChat.deleteStudent(userName);
//            MessSend messSend = new MessSend(userName, "out", roomChat.getAmount());
//            if(roomChat.getTeacherSession()!=null) sendMessage(roomChat.getTeacherSession(),messSend);
//        }
//    }
//
//    private void readList(){
//        List<String> list = roomChat.getStudentList();
//        list.remove("teacher");
//        MessSend messSend = new MessSend("teacher","readList",list);
//        String jsonInfo = new Gson().toJson(messSend);
//        roomChat.getTeacherSession().getAsyncRemote().sendText(jsonInfo); //通过session异步地将信息发送到客户端上
//    }
//
//    private void apply(String userName){
//        MessSend messSend = new MessSend(userName, "apply", roomChat.getAmount());
//        if( roomChat.getTeacherSession()!=null) sendMessage( roomChat.getTeacherSession(), messSend);
//    }
//
//    private void teacherApply(String userName){
//        MessSend messSend = new MessSend(userName, "teacherApply", roomChat.getAmount());
//        sendMessage(roomChat.getValue(userName), messSend);
//    }
//
//    private void cancelSence(String userName){
//        MessSend messSend = new MessSend(userName, "cancelSence", roomChat.getAmount());
//        sendMessage(roomChat.getValue(userName), messSend);
//    }
//
//    public static void sendMessage(Session session, MessSend messSend){
//        String jsonInfo = new Gson().toJson(messSend);		//将消息对象转换成json字符串
//        session.getAsyncRemote().sendText(jsonInfo); //通过session异步地将信息发送到客户端上
//    }
//}