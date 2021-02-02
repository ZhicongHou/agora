package com.hzcong.data.VO;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RoomChat {

    private String room;  //房间号
    private Session teacherSession = null;  //用于记录老师的session
    private ConcurrentMap<String, Session> studentMap = new ConcurrentHashMap<>();  //用于记录学生

    public RoomChat(String room) {
        this.room = room;
    }

    public String getRoom() {
        return room;
    }

    public void addStudent(String student, Session session) {
        studentMap.put(student, session);
    }

    public void deleteStudent(String student) {
        studentMap.remove(student);
    }

    public Session getTeacherSession() {
        return teacherSession;
    }

    public void setTeacherSession(Session teacherSession) {
        this.teacherSession = teacherSession;
    }

    public List<String> getStudentList() {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Session> entry : studentMap.entrySet()) {
            list.add(entry.getKey());
        }
        return list;
    }

    public int getAmount(){
        return studentMap.size();
    }

    public Session getValue(String student){
        return  studentMap.get(student);
    }
}
