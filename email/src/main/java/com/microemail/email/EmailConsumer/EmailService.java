package com.microemail.email.EmailConsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Value(value = "${spring.mail.username}")
    private String FROM;
    @Async
    public void sendEmail(String to, String subject, String template) {
        log.info("++++++++++++++++++++++++++++++++Send_Email+++++++++++++++++++++++");
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(FROM);
            helper.setTo(to);
            helper.setText(template, true);
            helper.setSubject(subject);
        };
        mailSender.send(mimeMessagePreparator);
    }
}
