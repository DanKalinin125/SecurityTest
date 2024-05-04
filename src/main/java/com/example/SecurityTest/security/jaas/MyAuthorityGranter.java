package com.example.SecurityTest.security.jaas;

import com.example.SecurityTest.entity.MyRole;
import com.example.SecurityTest.repository.MyUserRepository;
import lombok.Setter;
import org.springframework.security.authentication.jaas.AuthorityGranter;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Setter
public class MyAuthorityGranter implements AuthorityGranter {
    private MyUserRepository userRepository;
    @Override
    public Set<String> grant(Principal principal) {
        Set<String> stringRoles = new HashSet<>();
        for (MyRole role : userRepository.findByUsername(principal.getName()).getRoles()){
            stringRoles.add(role.getName());
        }
        return stringRoles;
    }
}
