package com.youtubeclonebynattarat.nattaratprojects.Service;

import com.youtubeclonebynattarat.nattaratprojects.Exceptios.ExceptionChat;
import com.youtubeclonebynattarat.nattaratprojects.DTO.ChatMessage;
import com.youtubeclonebynattarat.nattaratprojects.DTO.ChatMessageRequrest;
import com.youtubeclonebynattarat.nattaratprojects.Token.GetDataToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final SimpMessagingTemplate simpMessagingTemplate;


    public void postChat(ChatMessageRequrest request) throws ExceptionChat {
        Optional<String> opt = GetDataToken.getDataToken();
        if (opt.isEmpty()) {
            throw new ExceptionChat("Not available because it hasn't been login yet.");
        }
        final String destination = "/topic/chat";
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setFrom("USERID =");
        log.info("-----------------------------------" + request.getMessage());
        chatMessage.setMessage(request.getMessage());
        simpMessagingTemplate.convertAndSend(destination, chatMessage);
    }


}