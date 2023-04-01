package com.youtubeclonebynattarat.nattaratprojects.Service;

import com.youtubeclonebynattarat.nattaratprojects.Convert.ConvertsComponent;
import com.youtubeclonebynattarat.nattaratprojects.DTO.CommentDto;
import com.youtubeclonebynattarat.nattaratprojects.DTO.VideoDto;
import com.youtubeclonebynattarat.nattaratprojects.DTO.VideoModelResponseDto;
import com.youtubeclonebynattarat.nattaratprojects.Entity.Video;
import com.youtubeclonebynattarat.nattaratprojects.Exceptios.BaseException;
import com.youtubeclonebynattarat.nattaratprojects.Exceptios.ExceptionFile;
import com.youtubeclonebynattarat.nattaratprojects.Exceptios.ExceptionVideo;
import com.youtubeclonebynattarat.nattaratprojects.Mapper.CommentMapper;
import com.youtubeclonebynattarat.nattaratprojects.Mapper.VideoMapper;
import com.youtubeclonebynattarat.nattaratprojects.Repository.FileUploadRepository;
import com.youtubeclonebynattarat.nattaratprojects.Repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class VideosService {
    private final FileUploadRepository fileUploadRepository;
    private final VideoRepository videoRepository;
    private final UserService userService;
    private final ConvertsComponent convertsComponent;

//    public FileUpload saveFileImage(MultipartFile multipartFile) throws BaseException {
//        List<String> supportedTypes = List.of("image/jpeg");
//        if (!supportedTypes.contains(FileValidation(multipartFile))) {
//            throw new ExceptionFile("unsupported.file.type");
//        }
//        var fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
//        FileUpload fileUpload = new FileUpload();
//        try {
//            fileUpload.setFileName(fileName);
//            fileUpload.setFileType(multipartFile.getContentType());
//            fileUpload.setData(multipartFile.getBytes());
//            return fileUploadRepository.save(fileUpload);
//        } catch (IOException e) {
//            log.info("----ERROR---------RuntimeException--------CODE----------------");
//            throw new RuntimeException(e);
//        }
//    }
//
//    public FileUpload saveFileVideo(MultipartFile multipartFile) throws BaseException {
//        List<String> supportedTypes = List.of("video/mp4");
//        if (!supportedTypes.contains(FileValidation(multipartFile))) {
//            throw new ExceptionFile("unsupported.file.type");
//        }
//        var fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
//        FileUpload fileUpload = new FileUpload();
//        try {
//            fileUpload.setFileName(fileName);
//            fileUpload.setFileType(multipartFile.getContentType());
//            fileUpload.setData(multipartFile.getBytes());
//            return fileUploadRepository.save(fileUpload);
//        } catch (IOException e) {
//            log.info("----ERROR---------RuntimeException--------CODE----------------");
//            throw new RuntimeException(e);
//        }
//    }


    public VideoModelResponseDto uploadVideo(MultipartFile multipartFile) throws BaseException {
        List<String> supportedTypes = List.of("video/mp4");
        if (!supportedTypes.contains(FileValidation(multipartFile))) {
            throw new ExceptionFile("unsupported.file.type");
        }
        var video = new Video();
        try {
            video.setVideoData(multipartFile.getBytes());
        } catch (IOException e) {
            log.info("----ERROR---------RuntimeException--------CODE----------------");
            throw new RuntimeException(e);
        }
        var savevideo = videoRepository.save(video);
        return new VideoModelResponseDto(savevideo.getId(), savevideo.getVideoData());
    }

    public VideoDto editVideo(VideoDto videoDto) throws BaseException {
        var saveEditVideo = getVideoId(videoDto.getId());
        saveEditVideo.setTitle(videoDto.getTitle());
        saveEditVideo.setDescription(videoDto.getDescription());
        saveEditVideo.setTags(videoDto.getTags());
        saveEditVideo.setVedioStatus(videoDto.getVedioStatus());
        videoRepository.save(saveEditVideo);
        return videoDto;
    }

    public byte[] uploadThumbnail(MultipartFile multipartFile, String videoId) throws BaseException {
        List<String> supportedTypes = List.of("image/jpeg");
        if (!supportedTypes.contains(FileValidation(multipartFile))) {
            throw new ExceptionFile("unsupported.file.type");
        }
        var video = getVideoId(videoId);
        try {
            video.setVideoData(multipartFile.getBytes());
        } catch (IOException e) {
            log.info("----ERROR---------RuntimeException--------CODE----------------");
            throw new RuntimeException(e);
        }
        videoRepository.save(video);
        return video.getVideoData();
    }

    public VideoDto getVideoDetailsById(String videoId) throws BaseException {
        var videoById = getVideoId(videoId);
        incrementViewCountVideo(videoById);
        userService.addToVideoHistory(videoId);
        return VideoMapper.mapTovideoModelOpt(videoById);
    }

    public VideoDto likeVideo(String videoId) throws BaseException {
        var videoById = getVideoId(videoId);

        /**
         TODO เพิ่มจำนวนไลค์
         หากผู้ใช้ชอบวิดีโออยู่แล้ว ให้ลดจำนวนไลค์ลง

         like - 0 , dislike - 0
         like - 1 , dislike  - 0
         like - 0 , dislike  - 0

         like - 0 , dislike - 1
         like - 1 , dislike - 0

         หากผู้ใช้ไม่ชอบวิดีโออยู่แล้ว ให้เพิ่มจำนวนชอบและลดจำนวนไม่ชอบ
         **/
        if (userService.IF_likedVideos(videoId)) { // ถ้า true
            videoById.decrementLikes();   // ลบไลค์ video
            userService.removeToLikevideoId(videoId); // ลบไลค์ใน Set และ deleteLikeVideo
        } else if (userService.IF_dislikedVideos(videoId)) {
            videoById.decrementdislikes();
            userService.removeTodisLikevideoId(videoId);
            videoById.incrementLikes();
            userService.addToLikeVideo(videoId);
        }else {
            videoById.incrementLikes();
            userService.addToLikeVideo(videoId);
        }
        videoRepository.save(videoById);
        return VideoMapper.mapTovideoModelOpt(videoById);
    }


    public VideoDto dislikeVideo(String videoId) throws BaseException {
        var videoById = getVideoId(videoId);

        /**
         TODO เพิ่มจำนวนไลค์
         หากผู้ใช้ชอบวิดีโออยู่แล้ว ให้ลดจำนวนไลค์ลง

         like - 0 , dislike - 0
         like - 1 , dislike  - 0
         like - 0 , dislike  - 0

         like - 0 , dislike - 1
         like - 1 , dislike - 0

         หากผู้ใช้ไม่ชอบวิดีโออยู่แล้ว ให้เพิ่มจำนวนชอบและลดจำนวนไม่ชอบ
         **/
        if (userService.IF_dislikedVideos(videoId)) { // ถ้า true
            videoById.decrementdislikes();   // ลบไลค์ video
            userService.removeTodisLikevideoId(videoId); // ลบไลค์ใน Set และ deleteLikeVideo
        } else if (userService.IF_likedVideos(videoId)) {
            videoById.decrementLikes();
            userService.removeToLikevideoId(videoId);
            videoById.incrementdislikes();
            userService.addTodisLikeVideo(videoId);
        }else {
            videoById.incrementdislikes();
            userService.addTodisLikeVideo(videoId);
        }
        videoRepository.save(videoById);
        return VideoMapper.mapTovideoModelOpt(videoById);
    }

    // TODO Functions
    private Video getVideoId(String Id) throws BaseException {
        Optional<Video> optId = videoRepository.findById(Id);
        if (optId.isEmpty()) {
            throw new ExceptionVideo("videoId empty");
        }
        return optId.get();
    }

    private String FileValidation(MultipartFile multipartFile) throws BaseException {
        if (ObjectUtils.isEmpty(multipartFile)) {
            throw new ExceptionFile("File null");
        }
        if (multipartFile.getSize() > 1048576 * 15) {
            throw new ExceptionFile("File max.size");
        }
        var contentType = multipartFile.getContentType();
        if (contentType == null) {
            // throw error
            throw new ExceptionFile("unsupported.file.type");
        }
        return contentType;
    }

    private void incrementViewCountVideo(Video video){
        video.incrementViewCount();
        videoRepository.save(video);
    }

    public String addComment(String videoId, CommentDto commentDto) throws BaseException {
        Video videoById = getVideoId(videoId);
        if(ObjectUtils.isEmpty(commentDto.getCommentText())){
            throw new ExceptionVideo("commentDto Empty");
        }
        if(ObjectUtils.isEmpty(commentDto.getAuthorId())){
            throw new ExceptionVideo("commentDto Empty");
        }
        videoById.addComment(CommentMapper.mapToComments(commentDto));
        videoRepository.save(videoById);
        return "comment success";
    }


    // TODO VideoGetComment วิธีที่1
    public List<CommentDto> getAllComments(String videoId) throws BaseException {
        Video videoById = getVideoId(videoId);
        return convertsComponent.convertAllComment(videoById.getCommentLists());
    }

    // TODO VideoAll วิธีที่1
    public List<VideoDto> getAllVideo() {
        return convertsComponent.convertAllVideo(videoRepository.findAll());
    }




    // TODO VideoGetComment วิธีที่2
//    public List<CommentDto> getAllComments(String videoId) throws BaseException {
//        Video video = getVideoId(videoId);
//        List<Comments> commentList = video.getCommentLists();
//        return commentList.stream().map(comments -> C).toList();
//    }
//
//    private CommentDto mapToCommentDto(Comments comment) {
//        CommentDto commentDto = new CommentDto();
//        commentDto.setCommentText(comment.getText());
//        commentDto.setAuthorId(comment.getAuthorId());
//        return commentDto;
//    }

    // TODO VideoAll วิธีที่2
//    public List<VideoDto> getAllVideos() {
//        return videoRepository.findAll()
//                .stream().map(VideoMapper::mapTovideoModel).toList();
//    }

}














