package com.youtubeclonebynattarat.nattaratprojects.Service;

import com.microemail.common.Emailcommon.EmailRequest;
import com.youtubeclonebynattarat.nattaratprojects.Exceptios.BaseException;
import com.youtubeclonebynattarat.nattaratprojects.Exceptios.ExceptionEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final KafkaTemplate<String,EmailRequest> kafkaTemplate;

    public void sendActivateUserEmail(String email,String name,String token) throws BaseException  {
        String template = null;
        try {
            template = readEmailTemplate("email_activate.html");
        } catch (IOException e) {
            throw new ExceptionEmail("template not found");
        }
        String finalLink = "http://localhost:4200/activate/"+token;

        template = template.replace("${P_NAME}",name);
        template = template.replace("${P_LINK}",finalLink);
        String subject = "Please activate your account email";
        EmailRequest request = new EmailRequest();
        request.setTo(email);
        request.setTemplate(template);
        request.setSubject(subject);

        CompletableFuture<SendResult<String, EmailRequest>> future = kafkaTemplate.send("email_activated",request);
        future.whenComplete((success, throwable) -> {
            if (throwable != null) {
                log.error("Kafka send fail");
                log.error(String.valueOf(throwable));
            } else {
                log.info("kafka send success");
                log.info(String.valueOf(success));
            }
        });
    }

    private String readEmailTemplate(String filename) throws IOException {
        File file = ResourceUtils.getFile("classpath:Email/" + filename);
        return   FileCopyUtils.copyToString(new FileReader(file));
    }

}
