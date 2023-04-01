package com.youtubeclonebynattarat.nattaratprojects.Repository;

import com.youtubeclonebynattarat.nattaratprojects.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByUsername(String username);

    @Override
    Optional<User> findById(String id);

    Optional<User> findByToken(String token);

    Optional<User> findByEmailAddress(String email);
    Optional<User> findBySub(String sub);

}
