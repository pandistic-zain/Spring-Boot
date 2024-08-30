package org.zain.journalapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zain.journalapp.Entity.UserEntity;
import org.zain.journalapp.Services.UserServices;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserServices userServices;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/create-user")
    public ResponseEntity<String> saveUser(@RequestBody UserEntity user) {
        try {
            userServices.saveUser(user);
            return new ResponseEntity<>("User saved successfully", HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>("Failed to save user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
