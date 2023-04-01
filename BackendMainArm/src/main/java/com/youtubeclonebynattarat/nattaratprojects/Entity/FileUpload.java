package com.youtubeclonebynattarat.nattaratprojects.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "FileUpload")
@Data
public class FileUpload {
    @Id
    private String id;
    private String fileName;
    private String fileType;
    private byte[] data;

}
