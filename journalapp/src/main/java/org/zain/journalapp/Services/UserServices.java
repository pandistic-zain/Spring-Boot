package org.zain.journalapp.Services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zain.journalapp.DAO.UserRepository;
import org.zain.journalapp.Entity.UserEntity;

@Component
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    public String saveUser(UserEntity user) {
        userRepository.save(user);
        return "User Saved in Database";
    }

    public Optional<UserEntity> findByName(String name) {
        return userRepository.findByName(name);
    }

    public boolean deleteUser(ObjectId id) {
        userRepository.deleteById(id);
        return true;
    }

}
