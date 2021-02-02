package com.hzcong.data.jsonmsg;

public class MessageMsg {

    private int limit;
    private int count;

    public MessageMsg(int limit, int count) {
        this.limit = limit;
        this.count = count;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
