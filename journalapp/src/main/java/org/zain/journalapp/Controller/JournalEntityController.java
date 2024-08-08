package org.zain.journalapp.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.zain.journalapp.Entity.JournalEntity;
import org.zain.journalapp.Services.JournalEntityServices;

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
        try {
            // return new ArrayList<>(journalEntities.values());
            List<JournalEntity> entities = journalEntityServices.getAllEntities();
            return new ResponseEntity<>(entities, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{eid}")
    public ResponseEntity<JournalEntity> getById(@PathVariable ObjectId eid) {
        try {
            // return journalEntities.get(eid);
            Optional<JournalEntity> entity = journalEntityServices.findById(eid);
            if (entity.isPresent()) {
                return new ResponseEntity<>(entity.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Log the exception (e.g., using a logger)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<String> createEntity(@RequestBody JournalEntity entity) {
        try {
            // journalEntities.put(entity.getId(), entity);
            // return "Entity created";
            journalEntityServices.saveJournalEntity(entity);
            return new ResponseEntity<>("Entity Created in MongoDB", HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception (e.g., using a logger)
            return new ResponseEntity<>("Failed to create entity", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/id/{eid}")
    public ResponseEntity<String> deleteById(@PathVariable ObjectId eid) {
        try {
            // return journalEntities.remove(eid);
            boolean deleted = journalEntityServices.deleteJournalEntity(eid);
            if (deleted) {
                return new ResponseEntity<>("Entity Deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Entity Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Log the exception (e.g., using a logger)
            return new ResponseEntity<>("Failed to delete entity", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/id/{eid}")
    public ResponseEntity<String> putMethodName(@PathVariable ObjectId eid, @RequestBody JournalEntity newEntity) {
        try {
            // journalEntities.put(eid, entity);
            // return "Entity Updated";
            Optional<JournalEntity> optionalOld = journalEntityServices.findById(eid);
            if (optionalOld.isPresent()) {
                JournalEntity old = optionalOld.get();
                old.setTitle(newEntity.getTitle() != null && !newEntity.getTitle().isEmpty() ? newEntity.getTitle() : old.getTitle());
                old.setContent(newEntity.getContent() != null && !newEntity.getContent().isEmpty() ? newEntity.getContent() : old.getContent());
                journalEntityServices.saveJournalEntity(old);
                return new ResponseEntity<>("Entity Updated In Database", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Entity Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Log the exception (e.g., using a logger)
            return new ResponseEntity<>("Failed to update entity", HttpStatus.BAD_REQUEST);
        }
    }
}
