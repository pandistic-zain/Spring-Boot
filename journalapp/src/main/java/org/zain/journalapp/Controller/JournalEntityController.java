package org.zain.journalapp.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.zain.journalapp.Entity.JournalEntity;
import org.zain.journalapp.Entity.UserEntity;
import org.zain.journalapp.Services.JournalEntityServices;
import org.zain.journalapp.Services.UserServices;

// import java.util.ArrayList;
// import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// import java.util.Map;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private UserServices userServices;

    @GetMapping
    public ResponseEntity<List<JournalEntity>> getAllJournalEntriesOfUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        try {
            // return new ArrayList<>(journalEntities.values());
            UserEntity user = userServices.findByName(username);

            List<JournalEntity> entities = user.getJournalEntities();
            if (entities != null && !entities.isEmpty()) {
                return new ResponseEntity<>(entities, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/id/{eid}")
    public ResponseEntity<JournalEntity> getById(@PathVariable ObjectId eid) {
        try {
            // return journalEntities.get(eid);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            UserEntity user = userServices.findByName(username);
            List<JournalEntity> collection = user.getJournalEntities().stream()
                    .filter(x -> x.getId().equals(eid))
                    .collect(Collectors.toList());
            if (!collection.isEmpty()) {

                Optional<JournalEntity> entity = journalEntityServices.findById(eid);
                if (entity.isPresent()) {
                    return new ResponseEntity<>(entity.get(), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Log the exception (e.g., using a logger)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<String> createEntity(@RequestBody JournalEntity entity) {

        try {
            // journalEntities.put(entity.getId(), entity);
            // return "Entity created";
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            journalEntityServices.saveJournalEntity(entity, username);
            return new ResponseEntity<>("Entity Created in MongoDB", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create entity", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/id/{eid}")
    public ResponseEntity<String> deleteById(@PathVariable ObjectId eid) {
        try {
            // return journalEntities.remove(eid);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userName = auth.getName();
            boolean deleted = journalEntityServices.deleteJournalEntity(eid, userName);
            if (deleted) {
                return new ResponseEntity<>("Entity Deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Entity Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Log the exception (e.g., using a logger)
            return new ResponseEntity<>("Failed to delete entity", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/id/{eid}")
    public ResponseEntity<String> putMethodName(@PathVariable ObjectId eid, @RequestBody JournalEntity newEntity) {
        try {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            UserEntity user = userServices.findByName(username);
            List<JournalEntity> collection = user.getJournalEntities().stream()
                    .filter(x -> x.getId().equals(eid))
                    .collect(Collectors.toList());
            if (!collection.isEmpty()) {

                Optional<JournalEntity> optionalOld = journalEntityServices.findById(eid);
                if (optionalOld.isPresent()) {
                    JournalEntity old = optionalOld.get();
                    old.setTitle(newEntity.getTitle() != null && !newEntity.getTitle().isEmpty() ? newEntity.getTitle()
                            : old.getTitle());
                    old.setContent(
                            newEntity.getContent() != null && !newEntity.getContent().isEmpty() ? newEntity.getContent()
                                    : old.getContent());
                    journalEntityServices.saveJournalEntity(old);
                }
                return new ResponseEntity<>("Entity Updated In Database", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Entity Not Found", HttpStatus.NOT_FOUND);
            }

            // journalEntities.put(eid, entity);
            // return "Entity Updated";

        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update entity", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
