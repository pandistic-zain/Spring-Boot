package org.zain.journalapp.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.zain.journalapp.DAO.JournalEntityRepository;
import org.zain.journalapp.Entity.JournalEntity;
import org.zain.journalapp.Entity.UserEntity;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;

@Component
public class JournalEntityServices {

    @Autowired
    private JournalEntityRepository journalEntityRepository;

    @Autowired
    private UserServices userServices;

     @Autowired
    private MongoClient mongoClient;  

    public void saveJournalEntity(JournalEntity journalEntity, String username) throws Exception {
        ClientSession session = mongoClient.startSession();  // Use the injected MongoClient
        
        try {
            session.startTransaction();

            // Find user by username
            UserEntity user = userServices.findByName(username);

            // Save journal entry
            journalEntity.setDate(LocalDateTime.now());
            JournalEntity saved = journalEntityRepository.save(journalEntity);

            // Add journal to user
            user.getJournalEntities().add(saved);

            // Save the updated user
            userServices.saveNewUser(user);

            // Commit transaction
            session.commitTransaction();
        } catch (Exception e) {
            // Rollback transaction on error
            session.abortTransaction();
            System.out.println("Exception Occurred: " + e);
            throw new Exception("Error Occurred", e);
        } finally {
            session.close();
        }
    }


    public void saveJournalEntity(JournalEntity journalEntity) {
        journalEntity.setDate(LocalDateTime.now());
        journalEntityRepository.save(journalEntity);
    }

    public List<JournalEntity> getAllEntities() {
        return journalEntityRepository.findAll();
    }

    public Optional<JournalEntity> findById(ObjectId id) {
        return journalEntityRepository.findById(id);
    }

    public boolean deleteJournalEntity(ObjectId id, String username) throws Exception {
        UserEntity user = userServices.findByName(username);
        user.getJournalEntities().removeIf(x -> x.getId().equals(id));
        userServices.saveUser(user);
        journalEntityRepository.deleteById(id);
        return true;
    }
}
