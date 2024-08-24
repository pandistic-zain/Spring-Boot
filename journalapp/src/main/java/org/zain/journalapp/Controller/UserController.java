package org.zain.journalapp.Controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zain.journalapp.Entity.UserEntity;
import org.zain.journalapp.Services.UserServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            List<UserEntity> users = userServices.getAll(); 
            return new ResponseEntity<>(users, HttpStatus.OK); 
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody UserEntity user) {
        try {
            userServices.saveUser(user);
            return new ResponseEntity<>("User saved successfully", HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>("Failed to save user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@RequestBody UserEntity user, @PathVariable String username) {
        UserEntity userInDb = userServices.findByName(username);
        if (userInDb != null) {
            try {
                userInDb.setUserName(user.getUserName());
                userInDb.setPassword(user.getPassword());
                userServices.saveUser(userInDb);
                return new ResponseEntity<>("Update User Successfully",HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Failed to Update User", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>("User Not Found", HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/id/{uid}")
    public ResponseEntity<String> deleteUser(@PathVariable ObjectId uid){
        try {
            userServices.deleteUser(uid);
            return new ResponseEntity<>("User Deleted Successfully",HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Failed to Delete User", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 

}
