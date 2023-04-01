package com.youtubeclonebynattarat.nattaratprojects.Repository;

import com.youtubeclonebynattarat.nattaratprojects.Entity.FileUpload;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadRepository extends MongoRepository<FileUpload,String> {
}
