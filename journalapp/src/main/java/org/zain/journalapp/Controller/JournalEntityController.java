package org.zain.journalapp.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.zain.journalapp.Entity.JournalEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/journal")
public class JournalEntityController {
    private Map<Long, JournalEntity> journalEntities = new HashMap<>();

    @GetMapping
    public List<JournalEntity> getAll() {
        return new ArrayList<>(journalEntities.values());
    }
    // @GetMapping("/id/{eid}")
    // public ResponseEntity<JournalEntity> getById(@PathVariable Long eid) {
    //     JournalEntity journalEntity = journalEntities.get(eid);
    //     if (journalEntity != null) {
    //         return ResponseEntity.ok(journalEntity);
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    @GetMapping("/id/{eid}")
    public JournalEntity getById(@PathVariable long eid) {
        return  journalEntities.get(eid);
    }

    @PostMapping
    public String createEntity(@RequestBody JournalEntity entity) {
        journalEntities.put(entity.getId(), entity);
        return "Entity created";
    }

}
