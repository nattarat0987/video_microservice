package com.youtubeclonebynattarat.nattaratprojects.DTO;

import com.youtubeclonebynattarat.nattaratprojects.Entity.VedioStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto {
    private String id;
    private String title; //หัวข้อ
    private String description; // คำอธิบาย
    private Set<String> tags;
    private byte[] data;
    private VedioStatus vedioStatus;
    private byte[] thumbnaiData;
    private Integer likecount;
    private Integer dislikecout;
    private Integer viewCount;
}
