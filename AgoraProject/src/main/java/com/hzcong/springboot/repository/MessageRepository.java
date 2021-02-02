package com.hzcong.springboot.repository;

import com.hzcong.data.entities.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<MessageEntity,String> {

    Iterable<MessageEntity> getMessageEntitiesByReceiverId(String recieverId);

    Iterable<MessageEntity> getMessageEntitiesBySenderId(String senderId);

    @Query(value = "SELECT * FROM message WHERE message.receiver_id = :receiverId /*?#{#pageable}*/"
            ,countQuery="select count(*) from message WHERE message.receiver_id = :receiverId"
            ,nativeQuery = true)
    Page<MessageEntity> getMessageEntitiesByReceiverId(@Param("receiverId") String receiverId, Pageable page);

    @Query(value = "SELECT * FROM message WHERE message.receiver_id = :receiverId and readed = false"
            ,nativeQuery = true)
    Iterable<MessageEntity> getUnreadedMessageEntitiesByReceiverId(@Param("receiverId") String receiverId);

    @Modifying
    @Query(value = "update message set readed = :readed where receiver_id = :receiverId",nativeQuery = true)
    int updateReaded(@Param("readed")boolean readed,@Param("receiverId")String receiverId);

    @Query(value = "select  count(*) from  message where receiver_id = :receiverId",nativeQuery = true)
    int getMessageCountByRecieverId(@Param("receiverId") String receiverId);

}
