package com.youtubeclonebynattarat.nattaratprojects.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
@Data
@NoArgsConstructor
public class Comments {
    @Id
    private String id;
    private String text;
    private String authorId;//ผู้แต่ง
    private Integer likeCount;// ชอบกี่ครั้ง
    private Integer disLikeCount; // ไม่ชอบกี่ครั้ง

    public Comments(String text, String authorId) {
        this.text = text;
        this.authorId = authorId;
    }
}