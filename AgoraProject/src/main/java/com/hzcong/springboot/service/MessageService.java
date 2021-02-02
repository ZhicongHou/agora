package com.hzcong.springboot.service;

import com.hzcong.data.entities.MessageEntity;
import org.springframework.data.domain.Page;

public interface MessageService {

    Iterable<MessageEntity> getMessagesByRecieverId(String recieverId);

    Iterable<MessageEntity> getUnreadedMessagesByRecieverId(String recieverId);

    Iterable<MessageEntity> getMessgesBySenderId(String senderId);

    boolean addMessage(MessageEntity message);

    Page<MessageEntity> getMessagesByRecieverId(String recieverId, int pageNum, int pageSize);

    void updateReaded(boolean readed,String recieverId);

    int getMessageCountByRecieverId(String recieverId);

}
