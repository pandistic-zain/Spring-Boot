package org.zain.journalapp.Services;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zain.journalapp.DAO.UserRepository;
import org.zain.journalapp.Entity.UserEntity;

import com.mongodb.DuplicateKeyException;

@Component
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    public void saveUser(UserEntity user) throws Exception {
        try {
            userRepository.save(user);
        } catch (DuplicateKeyException e) {
            throw new Exception("User with the same username already exists.");
        }
    }

    public UserEntity findByName(String name) {
        return userRepository.findByUserName(name);
    }

    public boolean deleteUser(ObjectId id) {
        userRepository.deleteById(id);
        return true;
    }


}
