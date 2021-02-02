package com.hzcong.data.VO;

public class TaskVO {

    private String title;
    private String secId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public TaskVO(String title, String secId) {
        this.title = title;
        this.secId = secId;
    }
}
