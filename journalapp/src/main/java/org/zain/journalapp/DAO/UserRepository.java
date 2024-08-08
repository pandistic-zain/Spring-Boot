package org.zain.journalapp.DAO;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.zain.journalapp.Entity.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity ,ObjectId> {

    Optional<UserEntity> findByName(String name);
    
}
