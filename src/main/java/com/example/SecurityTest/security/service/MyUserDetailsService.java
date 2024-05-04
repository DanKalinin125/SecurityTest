package com.example.SecurityTest.security.service;

import com.example.SecurityTest.entity.MyUser;
import com.example.SecurityTest.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private MyUserRepository myUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = myUserRepository.findByUsername(username);
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown user: " + username);
        }
        return User.builder()
                .username(myUser.getUsername())
                .password(myUser.getPassword())
                .roles(String.valueOf(myUser.getRole()))
                .build();
    }
}
