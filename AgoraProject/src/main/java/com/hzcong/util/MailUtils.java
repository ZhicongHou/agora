package com.hzcong.util;
import com.hzcong.data.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

class Auth extends Authenticator {

    private String username = "";
    private String password = "";

    public Auth(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
}


@Service
public class MailUtils {

    private Properties props;// 系统属性
    private Session mailSession;// 邮件会话对象
    private MimeMessage mimeMsg; // MIME邮件对象
    private String smtpHost = "smtp.163.com";
    private String sender = "ZhicongHou@163.com";
    private String senderPassword = "happynewyear2019";
//    private String smtpHost = "smtp.qq.com";
//    private String sender = "1129743671@qq.com";
//    private String senderPassword = "dnndcivifmmggjac";

    public MailUtils(){
        Auth au = new Auth(sender, senderPassword);
        props = System.getProperties(); // 获得系统属性对象
        props.put("mail.smtp.host", smtpHost); // 设置SMTP主机
        props.put("mail.smtp.auth", "true");// 同时通过验证
        // 获得邮件会话对象
        mailSession = Session.getInstance(props, au);
    }

    public boolean  sendChangePasswordMail(String receiver,  String encodeString){
        String body = "<!DOCTYPE html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>忘记密码</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "链接："+"<a href='https://noi.uutime.cn/changePassword?encodeString="+encodeString+"'>重置密码</a>\n"+
                "</body>\n" +
                "</html>";
        return sendingMimeMail(receiver,"重置密码",body);
    }

    public boolean  sendRegisterMail(String receiver,  String encodeString){
        String body = "<!DOCTYPE html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>激活用户</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "链接："+"<a href='https://noi.uutime.cn/activate?encodeString="+encodeString+"'>激活用户</a>\n"+
                "</body>\n" +
                "</html>";
        return sendingMimeMail(receiver,"重置密码",body);
    }

    public boolean  sendSuccessfullyRegisteredMail(String receiver, String userName){
        String body = "<!DOCTYPE html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>激活用户成功</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "成功注册，您的用户名为："+userName+"激活用户</a>\n"+
                "</body>\n" +
                "</html>";
        return sendingMimeMail(receiver,"重置密码",body);
    }

    public boolean sendingMimeMail( String MailTo, String MailSubject,String MailBody){
        return sendingMimeMail(MailTo, null,null,MailSubject,  MailBody);
    }

    public boolean sendingMimeMail(String MailTo,
                                   String MailCopyTo, String MailBCopyTo, String MailSubject,
                                   String MailBody) {
        try {
            // 创建MIME邮件对象
            mimeMsg = new MimeMessage(mailSession);
            // 设置发信人
            mimeMsg.setFrom(new InternetAddress(sender));
            // 设置收信人
            if (MailTo != null) {
                mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress
                        .parse(MailTo));
            }
            // 设置抄送人
            if (MailCopyTo != null) {
                mimeMsg.setRecipients(Message.RecipientType.CC,
                        InternetAddress.parse(MailCopyTo));
            }
            // 设置暗送人
            if (MailBCopyTo != null) {
                mimeMsg.setRecipients(Message.RecipientType.BCC,
                        InternetAddress.parse(MailBCopyTo));
            }
            // 设置邮件主题
            mimeMsg.setSubject(MailSubject, "utf-8");
            // 设置邮件内容，将邮件body部分转化为HTML格式
            mimeMsg.setContent(MailBody, "text/html;charset=utf-8");
            // 发送邮件
            Transport.send(mimeMsg);
            return true;
        } catch (Exception e) {
//            e.printStackTrace();
            return false;
        }
    }
}
