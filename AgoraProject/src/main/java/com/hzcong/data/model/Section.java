package com.hzcong.data.model;

public class Section {

    private String teacherUid;
    private String studentUid;
    private String room;

    public String getTeacherUid() {
        return teacherUid;
    }

    public String getStudentUid() {
        return studentUid;
    }

    public String getRoom() {
        return room;
    }

    public void setTeacherUid(String teacherUid) {
        this.teacherUid = teacherUid;
    }

    public void setStudentUid(String studentUid) {
        this.studentUid = studentUid;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Section(String teacherUid, String studentUid, String room) {
        this.teacherUid = teacherUid;
        this.studentUid = studentUid;
        this.room = room;
    }

    @Override
    public String toString() {
        return "Section{" +
                "teacherUid='" + teacherUid + '\'' +
                ", studentUid='" + studentUid + '\'' +
                ", room='" + room + '\'' +
                '}';
    }
}
