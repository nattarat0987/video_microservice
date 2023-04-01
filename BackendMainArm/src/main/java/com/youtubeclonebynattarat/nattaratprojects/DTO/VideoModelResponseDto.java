package com.youtubeclonebynattarat.nattaratprojects.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VideoModelResponseDto {
    private String videoId;
    private byte[] videoData;
}
