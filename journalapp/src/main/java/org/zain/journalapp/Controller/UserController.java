package org.zain.journalapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zain.journalapp.DAO.UserRepository;
import org.zain.journalapp.Entity.UserEntity;
import org.zain.journalapp.Services.UserServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServices userServices;
    @Autowired
    private UserRepository userRepository;
    // This will be implemented in Admin's Controller where the Admin will have the access to see all the users!
/*
    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        try {
            List<UserEntity> users = userServices.getAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
*/
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntity user) {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserEntity userInDb = userServices.findByName(username);

        try {
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userServices.saveUser(userInDb);
            return new ResponseEntity<>("Update User Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to Update User", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            userRepository.deleteByUsername(auth.getName());
            return new ResponseEntity<>("User Deleted Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to Delete User", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
