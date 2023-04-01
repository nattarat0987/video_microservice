package com.microemail.email.Service;

import com.microemail.common.Emailcommon.EmailRequest;
import com.microemail.email.EmailConsumer.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailListenable {

    private final EmailService emailService;

    public EmailListenable(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "email_activated")
    private void listenableForActivatedEmail(EmailRequest request){
        log.info("Kafka_Received============================"+request.getTo());
        emailService.sendEmail(request.getTo(),request.getSubject(),request.getTemplate());
    }
}
