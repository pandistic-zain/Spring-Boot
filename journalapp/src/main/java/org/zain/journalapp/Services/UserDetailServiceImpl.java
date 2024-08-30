package org.zain.journalapp.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.zain.journalapp.DAO.UserRepository;
import org.zain.journalapp.Entity.UserEntity;

@Component
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByUserName(username);
        if (user != null) {
            UserDetails userdetails = User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();

            return userdetails;
        }
       throw new UsernameNotFoundException("User Not Found With This UserName : "+username);
    }
}