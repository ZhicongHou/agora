package com.hzcong.data.VO;

public class MessSend {
    private String userName;
    private String status;
    private Object data;

    public MessSend(String userName, String status, Object data) {
        this.userName = userName;
        this.status = status;
        this.data = data;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
