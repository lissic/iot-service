package com.hlytec.cloud.message.mail;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: MailClient
 * @author: zero
 * @date: 2021/4/30 14:50
 */
@Component
@Slf4j
public class MailClient {

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送邮件
     *
     * @param content 发送内容
     * @param subject 发送标题
     * @param to 接收人
     */
    public void sendMail(String content, String subject, String to) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e){
            log.error("send mail failed:{}", e.getMessage());
        }
    }
}
