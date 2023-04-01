package com.youtubeclonebynattarat.nattaratprojects.Mapper;

import com.youtubeclonebynattarat.nattaratprojects.Entity.Video;
import com.youtubeclonebynattarat.nattaratprojects.Exceptios.BaseException;
import com.youtubeclonebynattarat.nattaratprojects.Exceptios.ExceptionVideo;
import com.youtubeclonebynattarat.nattaratprojects.DTO.VideoDto;

import java.util.Optional;

public class VideoMapper {
    private VideoMapper(){

    }

    public static VideoDto mapTovideoModelOpt(Video video) throws BaseException {
        return Optional.ofNullable(video).map(videoEntity ->
                new VideoDto(
                        videoEntity.getId(),
                        videoEntity.getTitle(),
                        videoEntity.getDescription(),
                        videoEntity.getTags(),
                        videoEntity.getVideoData(),
                        videoEntity.getVedioStatus(),
                        videoEntity.getThumbnaiData(),
                        videoEntity.getLikes().get(),
                        videoEntity.getDislikes().get(),
                        videoEntity.getViewCount().get()
                )
                ).orElseThrow(() -> new ExceptionVideo("optEmpty value"));
    }

    public static VideoDto mapTovideoModel(Video video)  {
        return new VideoDto(
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
                );
    }

}
