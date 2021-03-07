package com.hzcong.data.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "message")
public class MessageEntity  implements Serializable {
    private String id;
    private String content;
    private String senderId;
    private String senderName;
    private String receiverId;
    private String receiverName;
    private boolean readed;

    @Id
    @Column(name = "id", columnDefinition = "char(32)")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "sender_id", columnDefinition = "char(32)")
    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    @Basic
    @Column(name = "sender_name")
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    @Basic
    @Column(name = "receiver_id", columnDefinition = "char(32)")
    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    @Basic
    @Column(name = "receiver_name")
    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @Basic
    @Column(name = "readed")
    public boolean isReaded() {
        return readed;
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageEntity that = (MessageEntity) o;

        if (readed != that.readed) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (senderId != null ? !senderId.equals(that.senderId) : that.senderId != null) return false;
        if (senderName != null ? !senderName.equals(that.senderName) : that.senderName != null) return false;
        if (receiverId != null ? !receiverId.equals(that.receiverId) : that.receiverId != null) return false;
        if (receiverName != null ? !receiverName.equals(that.receiverName) : that.receiverName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (senderId != null ? senderId.hashCode() : 0);
        result = 31 * result + (senderName != null ? senderName.hashCode() : 0);
        result = 31 * result + (receiverId != null ? receiverId.hashCode() : 0);
        result = 31 * result + (receiverName != null ? receiverName.hashCode() : 0);
        return result;
    }
}
