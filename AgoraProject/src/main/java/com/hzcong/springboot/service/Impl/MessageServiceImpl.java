package com.hzcong.springboot.service.Impl;

import com.hzcong.data.entities.MessageEntity;
import com.hzcong.springboot.repository.MessageRepository;
import com.hzcong.springboot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Iterable<MessageEntity> getMessagesByRecieverId(String recieverId) {
        return messageRepository.getMessageEntitiesByReceiverId(recieverId);
    }

    @Override
    public Iterable<MessageEntity> getUnreadedMessagesByRecieverId(String recieverId) {
        return messageRepository.getUnreadedMessageEntitiesByReceiverId(recieverId);
    }

    @Override
    public Iterable<MessageEntity> getMessgesBySenderId(String senderId) {
        return messageRepository.getMessageEntitiesBySenderId(senderId);
    }

    @Override
    public boolean addMessage(MessageEntity message) {
        return messageRepository.save(message)!=null;
    }

    @Override
    @Transactional
    public Page<MessageEntity> getMessagesByRecieverId(String recieverId, int pageNum, int pageSize) {
        Pageable page=new PageRequest(pageNum,pageSize);
        return messageRepository.getMessageEntitiesByReceiverId(recieverId,page);
    }

    @Override
    @Transactional
    public void updateReaded(boolean readed,String recieverId) {
        messageRepository.updateReaded(readed, recieverId);
    }

    @Override
    public int getMessageCountByRecieverId(String recieverId) {
        return messageRepository.getMessageCountByRecieverId(recieverId);
    }
}
