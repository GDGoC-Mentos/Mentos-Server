package com.mentos.mentosback.users.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setFrom(new InternetAddress("menttos12@gmail.com")); // 발신자 이메일
            message.setRecipients(Message.RecipientType.TO, to);  // 사용자가 입력한 이메일
            message.setSubject(subject);

            // HTML 형식 적용
            String htmlContent = content + "<br><br> 또는 URL을 복사해서 브라우저에 붙여넣으세요.<br>";
            message.setContent(htmlContent, "text/html; charset=utf-8");

            mailSender.send(message);
        } catch (MailException | MessagingException e) {
            throw new RuntimeException("이메일 발송 중 오류가 발생했습니다.", e);
        }
    }
}