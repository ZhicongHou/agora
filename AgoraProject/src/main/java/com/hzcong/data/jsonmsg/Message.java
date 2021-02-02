package com.hzcong.data.jsonmsg;

public class Message {

    private String flag;
    private String content;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Message() {
        flag = null;
        content = null;
    }

    public Message(String flag,String content) {
        this.flag = flag;
        this.content = content;
    }


    public void setFlagAndContent(String flag, String content){
        this.flag = flag;
        this.content = content;
    }
}
