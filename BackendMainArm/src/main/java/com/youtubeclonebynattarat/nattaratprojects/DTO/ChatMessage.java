package com.youtubeclonebynattarat.nattaratprojects.DTO;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ChatMessage {

    private String from;
    private String message;
    private LocalDateTime localDateTime ;
    public ChatMessage(){
        localDateTime = LocalDateTime.now();
    }

}
