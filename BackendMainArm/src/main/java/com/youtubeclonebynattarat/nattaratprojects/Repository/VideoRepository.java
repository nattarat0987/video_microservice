package com.youtubeclonebynattarat.nattaratprojects.Repository;

import com.youtubeclonebynattarat.nattaratprojects.Entity.Video;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends MongoRepository<Video,String> {
}
