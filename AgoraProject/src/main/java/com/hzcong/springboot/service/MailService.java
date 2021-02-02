package com.hzcong.springboot.service;


public interface MailService {

    boolean sendSimpleMail(String to, String subject, String content);
    boolean sendHtmlMail(String to, String subject, String content);
    boolean sendAttachmentsMail(String to, String subject, String content, String filePath);

}
