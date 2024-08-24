package org.zain.journalapp.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.zain.journalapp.Entity.JournalEntity;
import org.zain.journalapp.Entity.UserEntity;
import org.zain.journalapp.Services.JournalEntityServices;
import org.zain.journalapp.Services.UserServices;

import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.HashMap;
import java.util.List;
import java.util.Optional;
// import java.util.Map;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/journal")
public class JournalEntityController {
    // private Map<Long, JournalEntity> journalEntities = new HashMap<>();
    @Autowired
    private JournalEntityServices journalEntityServices;

    @GetMapping
    public ResponseEntity<List<JournalEntity>> getAll() {
        // return new ArrayList<>(journalEntities.values());
        List<JournalEntity> allEntities = journalEntityServices.getAllEntities();

        if (allEntities != null) {
            return new ResponseEntity<>(allEntities, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{eid}")
    public ResponseEntity<JournalEntity> getById(@PathVariable ObjectId eid) {
        // return journalEntities.get(eid);
        Optional<JournalEntity> journalentity = journalEntityServices.findById(eid);
        if (journalentity.isPresent()) {
            return new ResponseEntity<>(journalentity.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntity> createEntity(@RequestBody JournalEntity entity) {
        // journalEntities.put(entity.getId(), entity);
        // return "Entity created";
        try {
            entity.setDate(LocalDateTime.now());
            journalEntityServices.saveJournalEntity(entity);
            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/id/{eid}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId eid) {
        // return journalEntities.remove(eid);
        journalEntityServices.deleteJournalEntity(eid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{eid}")
    public ResponseEntity<JournalEntity> putMethodName(@PathVariable ObjectId eid,
            @RequestBody JournalEntity newEntity) {
        // journalEntities.put(eid, entity);
        // return "Entity Updated";
        JournalEntity old = journalEntityServices.findById(eid).orElse(null);
        if (old != null) {
            old.setTitle(
                    newEntity.getTitle() != null && newEntity.getTitle() != "" ? newEntity.getTitle() : old.getTitle());
            old.setContent(newEntity.getContent() != null && newEntity.getContent() != "" ? newEntity.getContent()
                    : old.getContent());

            journalEntityServices.saveJournalEntity(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
