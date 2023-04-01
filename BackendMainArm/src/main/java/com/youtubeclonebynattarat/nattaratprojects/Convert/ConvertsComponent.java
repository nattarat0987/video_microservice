package com.youtubeclonebynattarat.nattaratprojects.Convert;

import com.youtubeclonebynattarat.nattaratprojects.DTO.CommentDto;
import com.youtubeclonebynattarat.nattaratprojects.DTO.VideoDto;
import com.youtubeclonebynattarat.nattaratprojects.Entity.Comments;
import com.youtubeclonebynattarat.nattaratprojects.Entity.Video;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConvertsComponent {
    public List<CommentDto>  convertAllComment(List<Comments> comments){
        return comments
                .stream()
                .map(comment -> new CommentDto(
                        comment.getText(),
                        comment.getAuthorId()
                )).collect(Collectors.toList());
    }
    public List<VideoDto>  convertAllVideo(List<Video> videos){
        return videos
                .stream()
                .map(video -> new VideoDto(
                        video.getId(),
                        video.getTitle(),
                        video.getDescription(),
                        video.getTags(),
                        video.getVideoData(),
                        video.getVedioStatus(),
                        video.getThumbnaiData(),
                        video.getLikes().get(),
                        video.getDislikes().get(),
                        video.getViewCount().get()
                        )
                ).collect(Collectors.toList());
    }

}
