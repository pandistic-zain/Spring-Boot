package org.zain.journalapp.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.zain.journalapp.Entity.JournalEntity;
import org.zain.journalapp.Services.JournalEntityServices;

import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.HashMap;
import java.util.List;
// import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<JournalEntity> getAll() {
        // return new ArrayList<>(journalEntities.values());
        return journalEntityServices.getAllEntities();
    }

    @GetMapping("/id/{eid}")
    public JournalEntity getById(@PathVariable ObjectId eid) {
        // return journalEntities.get(eid);
        return journalEntityServices.findById(eid).orElse(null);
    }

    @PostMapping
    public String createEntity(@RequestBody JournalEntity entity) {
        // journalEntities.put(entity.getId(), entity);
        // return "Entity created";
        journalEntityServices.saveJournalEntity(entity);
        return "Entity Created in MongoDb";
    }

    @DeleteMapping("/id/{eid}")
    public boolean deleteById(@PathVariable ObjectId eid) {
        // return journalEntities.remove(eid);
        journalEntityServices.deleteJournalEntity(eid);
        return true;
    }

    @PutMapping("id/{eid}")
    public String putMethodName(@PathVariable ObjectId eid, @RequestBody JournalEntity newEntity) {
        // journalEntities.put(eid, entity);
        // return "Entity Updated";
        JournalEntity old = journalEntityServices.findById(eid).orElse(null);
        if(old!=null){
            old.setTitle(newEntity.getTitle() != null && newEntity.getTitle() != "" ?newEntity.getTitle() : old.getTitle());
            old.setContent(newEntity.getContent() != null && newEntity.getContent() != "" ? newEntity.getContent() : old.getContent());
        }
        journalEntityServices.saveJournalEntity(old);
        return "Entity Updated In Database";
    }
}
