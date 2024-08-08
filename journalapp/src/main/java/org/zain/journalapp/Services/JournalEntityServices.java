package org.zain.journalapp.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zain.journalapp.DAO.JournalEntityRepository;
import org.zain.journalapp.Entity.JournalEntity;

@Component
public class JournalEntityServices {
    @Autowired
    private JournalEntityRepository journalEntityRepository;

    public void saveJournalEntity(JournalEntity journalEntity ){
        journalEntity.setDate(LocalDateTime.now());
        journalEntityRepository.save(journalEntity);
    }
    
    public List<JournalEntity> getAllEntities(){
        return journalEntityRepository.findAll();
    }
    public Optional<JournalEntity>findById(ObjectId id){
        return journalEntityRepository.findById(id);
    }
    public boolean deleteJournalEntity(ObjectId id){
        journalEntityRepository.deleteById(id);
        return true;
    }
}
