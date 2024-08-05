package org.zain.journalapp.DAO;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.zain.journalapp.Entity.JournalEntity;

public interface JournalEntityRepository extends MongoRepository<JournalEntity ,ObjectId> {
    
}
