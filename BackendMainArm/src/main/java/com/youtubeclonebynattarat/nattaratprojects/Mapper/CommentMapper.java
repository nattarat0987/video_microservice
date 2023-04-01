package com.youtubeclonebynattarat.nattaratprojects.Mapper;

import com.youtubeclonebynattarat.nattaratprojects.DTO.CommentDto;
import com.youtubeclonebynattarat.nattaratprojects.Entity.Comments;
import com.youtubeclonebynattarat.nattaratprojects.Exceptios.BaseException;
import com.youtubeclonebynattarat.nattaratprojects.Exceptios.ExceptionVideo;

import java.util.Optional;

public class CommentMapper {

    private CommentMapper(){

    }
    public static Comments mapToComments(CommentDto commentDto) throws BaseException {
        return Optional.ofNullable(commentDto)
                .map(commentDto1 -> new Comments(
                        commentDto1.getCommentText(),
                        commentDto1.getAuthorId()
                )).orElseThrow(() -> new ExceptionVideo("Comments Empty"));
    }
}
