package com.hzcong.data.VO;

public class MessGet {
    private String room;
    private String userName;
    private String status;

    public MessGet(String room, String userName, String status) {
        this.room = room;
        this.userName = userName;
        this.status = status;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
