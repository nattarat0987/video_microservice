package com.youtubeclonebynattarat.nattaratprojects.Api;

import com.youtubeclonebynattarat.nattaratprojects.Exceptios.ExceptionChat;
import com.youtubeclonebynattarat.nattaratprojects.DTO.ChatMessageRequrest;
import com.youtubeclonebynattarat.nattaratprojects.Service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/chat")
public class ChatApi {
    private final ChatService chatService;

    public ChatApi(ChatService chatService) {
        this.chatService = chatService;
    }
    @PostMapping("/message")
    public ResponseEntity<Void> postChat(@RequestBody ChatMessageRequrest requrest) throws ExceptionChat {
            chatService.postChat(requrest);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
}
