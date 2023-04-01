package com.youtubeclonebynattarat.nattaratprojects.Api;

import com.youtubeclonebynattarat.nattaratprojects.DTO.CommentDto;
import com.youtubeclonebynattarat.nattaratprojects.DTO.VideoDto;
import com.youtubeclonebynattarat.nattaratprojects.DTO.VideoModelResponseDto;
import com.youtubeclonebynattarat.nattaratprojects.Exceptios.BaseException;
import com.youtubeclonebynattarat.nattaratprojects.Service.VideosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideosApi {
    private final VideosService videosService;

    @PostMapping("/uploadVideo")
    public ResponseEntity<VideoModelResponseDto>  uploadVideo(@RequestParam("file")MultipartFile multipartFile) throws BaseException {
        VideoModelResponseDto Response = videosService.uploadVideo(multipartFile);
        return ResponseEntity.ok(Response);
    }

    @PostMapping("/upload-thumbnail")
    public ResponseEntity<byte[]>  uploadThumbnail(@RequestParam("file")MultipartFile multipartFile,
                                                 @RequestParam("videoId")String videoId) throws BaseException {
        byte[] response = videosService.uploadThumbnail(multipartFile, videoId);
        return  ResponseEntity.ok(response);
    }

    @PutMapping("/edit-video")
    public ResponseEntity<VideoDto> editVideo(@RequestBody VideoDto request) throws BaseException {
        VideoDto response = videosService.editVideo(request);
        return  ResponseEntity.ok(response);
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<VideoDto> getVideoDetailsById(@PathVariable String videoId ) throws BaseException {
        VideoDto response = videosService.getVideoDetailsById(videoId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{videoId}/like")
    public ResponseEntity<VideoDto> likeVideo(@PathVariable String videoId) throws BaseException {
        VideoDto response = videosService.likeVideo(videoId);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/{videoId}/dislike")
    public ResponseEntity<VideoDto> dislikeVideo(@PathVariable String videoId) throws BaseException {
        VideoDto response = videosService.dislikeVideo(videoId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{videoId}/comment")
    public ResponseEntity<String>  addComment(@PathVariable String videoId , @RequestBody CommentDto commentDto) throws BaseException {
        String response = videosService.addComment(videoId,commentDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{videoId}/getAll-comment")
    public ResponseEntity<List<CommentDto>>  getAllComments(@PathVariable String videoId) throws BaseException {
        List<CommentDto> response = videosService.getAllComments(videoId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll-video")
    public ResponseEntity<List<VideoDto>>  getAllVideo(){
        return ResponseEntity.ok(videosService.getAllVideo());
    }

























}
