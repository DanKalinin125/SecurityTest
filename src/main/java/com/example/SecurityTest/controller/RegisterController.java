package com.example.SecurityTest.controller;

import com.example.SecurityTest.dto.RegisterDTO;
import com.example.SecurityTest.entity.MyRole;
import com.example.SecurityTest.entity.MyUser;
import com.example.SecurityTest.repository.MyRoleRepository;
import com.example.SecurityTest.repository.MyUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class RegisterController {
    private MyUserRepository myUserRepository;
    private MyRoleRepository myRoleRepository;
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        System.out.println(registerDTO.getUsername());

        // add check for username exists in a DB
        if (myUserRepository.existsByUsername(registerDTO.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if (myUserRepository.existsByEmail(registerDTO.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        MyUser myUser = MyUser.builder()
                .username(registerDTO.getUsername())
                .email(registerDTO.getEmail())
                .password(passwordEncoder.encode(registerDTO.getPassword())).build();

        MyRole myRole = myRoleRepository.findByName("ROLE_USER");
        myUser.setRole(myRole);

        myUserRepository.save(myUser);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }


}
