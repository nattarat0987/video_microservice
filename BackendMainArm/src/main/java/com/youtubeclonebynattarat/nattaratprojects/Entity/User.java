package com.youtubeclonebynattarat.nattaratprojects.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Document(collection = "User" )
@Data
@NoArgsConstructor
public class User {
    private String id;
    private String username;
    @JsonIgnore
    private String password;
    private String emailAddress;
    private String firstname;
    private String lastname;
    private String fullName;
    private String picture; // รูปภาพ
    private String sub;
    private Set<String> subscribedToUser = ConcurrentHashMap.newKeySet(); //สมัครสมาชิก
    private Set<String> Subscribers = ConcurrentHashMap.newKeySet(); //สมาชิก
    private Set<String> videoHistory = ConcurrentHashMap.newKeySet();//ประวัติวิดีโอ
    private Set<String> likedVideos = ConcurrentHashMap.newKeySet();//ชอบวิดีโอ
    private Set<String> dislikedVideos = ConcurrentHashMap.newKeySet(); //วิดีโอที่ไม่ชอบ
    private Boolean  activated;
    private String token;
    private Date tokenExpired;

    public void addToVideoHistory(String videoId){
        videoHistory.add(videoId);
    }
    public void addToLikeVideo(String videoId) {
        likedVideos.add(videoId);
    }
    public void addTodisLikeVideo(String videoId) {
        dislikedVideos.add(videoId);
    }
    public void removeToLikevideoId(String videoId) {
        likedVideos.remove(videoId);
    }
    public void removeTodisLikevideoId(String videoId) {
        dislikedVideos.remove(videoId);
    }

    public void addToSubscribedToUser(String userId){
        subscribedToUser.add(userId);
    }
    public void addToSubscribers(String userId){
        Subscribers.add(userId);
    }

    public void removeToSubscribedToUser(String userId){
        subscribedToUser.remove(userId);
    }
    public void removeToSubscribers(String userId){
        Subscribers.remove(userId);
    }
}
