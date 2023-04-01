package com.youtubeclonebynattarat.nattaratprojects.Entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Document(collection = "Video" )
@Data
@NoArgsConstructor
public class Video {
    @Id
    private String id;
    private String description; // คำอธิบาย
    private String title; //หัวข้อ
    private String userId;
    private AtomicInteger likes = new AtomicInteger(0);
    private AtomicInteger dislikes = new AtomicInteger(0);
    private Set<String> tags; //แทร็ก
    private byte[] videoData;
    private VedioStatus vedioStatus; //สถานะวีดีโอ
    private AtomicInteger  viewCount = new AtomicInteger(0); // จำนวนการดู
    private byte[] thumbnaiData;
    private List<Comments> commentLists = new CopyOnWriteArrayList<>();


    public void incrementViewCount(){
        viewCount.incrementAndGet();
    }

    // TODO เพิ่มไลค์ ให้วีดีโอ
    public void incrementLikes(){
        likes.incrementAndGet();
    }
    // TODO เพิ่มไลค์ แล้วกด เพิ่มอีกรอบเพื่อไม่ like ให้วีดีโอ
    public void decrementLikes(){
        likes.decrementAndGet();
    }
    // TODO เพิ่มไลค์ ให้วีดีโอ จาก dislikes
    public void incrementdislikes(){
        dislikes.incrementAndGet();
    }
    // TODO dislikes ให้วีดีโอ
    public void decrementdislikes(){
        dislikes.decrementAndGet();
    }

    public void addComment(Comments comments){
        commentLists.add(comments);
    }
}
