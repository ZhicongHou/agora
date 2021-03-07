package com.hzcong.springboot.controller;

import com.hzcong.data.entities.UserEntity;
import com.hzcong.springboot.service.UserService;
import com.hzcong.util.MailUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    UserService userService;

    /**
     * 定义此消费者接收topics = "demo"的消息，与controller中的topic对应上即可
     * @param record 变量代表消息本身，可以通过ConsumerRecord<?,?>类型的record变量来打印接收的消息的各种信息
     */
    @KafkaListener(topics = "sell_registered_mail")
    public void listen1 (ConsumerRecord<?, ?> record){
        System.out.printf("Listen1：topic is %s, offset is %d, value is %s \n", record.topic(), record.offset(), record.value());
        UserEntity user = userService.getUserByUserId((String)record.value());
        MailUtils mailUtils = new MailUtils();
        boolean flag = mailUtils.sendSuccessfullyRegisteredMail(user.getEmail(),user.getUserName());
        if(flag){
            System.out.println("发送成功");
        }else{
            System.out.println("发送失败");
        }
    }
}
