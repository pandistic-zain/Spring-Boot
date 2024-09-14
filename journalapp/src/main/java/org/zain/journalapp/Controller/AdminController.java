package org.zain.journalapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zain.journalapp.Entity.UserEntity;
import org.zain.journalapp.Services.UserServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserServices userServices;

    @GetMapping("/all-users")
    public ResponseEntity<?> GetAllUsers() {
        List<UserEntity> all = userServices.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(all, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<?> CreateAdminUser(@RequestBody UserEntity user) throws Exception {
        boolean created = false;
        userServices.createAdmin(user);
        created = true;
        if (created) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
}