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
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServices userServices;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        try {
            userServices.getAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody UserEntity user) {
       try {
        userServices.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
       } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       } 
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntity user) {
        UserEntity userInDb = userServices.findByName(user.getUserName());
        if (userInDb != null) {
            try {
                userInDb.setUserName(user.getUserName());
                userInDb.setPassword(user.getPassword());
                userServices.saveUser(userInDb);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Failed to Update User", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>("User Not Found", HttpStatus.BAD_REQUEST);
    }

}
