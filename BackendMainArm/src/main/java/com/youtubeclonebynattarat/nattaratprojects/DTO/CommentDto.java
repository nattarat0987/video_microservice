package com.youtubeclonebynattarat.nattaratprojects.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private String commentText;
    private String authorId;
}
