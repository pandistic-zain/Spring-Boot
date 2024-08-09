package org.zain.journalapp.DAO;



import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.zain.journalapp.Entity.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity ,ObjectId> {

    UserEntity findByName(String name);
    
}
