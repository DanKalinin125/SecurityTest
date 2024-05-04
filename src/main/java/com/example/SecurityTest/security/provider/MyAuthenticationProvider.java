package com.example.SecurityTest.security.provider;

import com.example.SecurityTest.entity.MyRole;
import com.example.SecurityTest.entity.MyUser;
import com.example.SecurityTest.security.service.MyUserDetailsService;
import com.example.SecurityTest.security.userdetails.MyUserDetails;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

@Setter
public class MyAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        //получаем пользователя
        MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(username);

        //смотрим, найден ли пользователь в базе
        if (userDetails == null) {
            throw new BadCredentialsException("Unknown user " + username);
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Bad password");
        }

        UserDetails principal = User.builder()
                .username(userDetails.getUsername())
                .password(userDetails.getPassword())
                .authorities(userDetails.getAuthorities())
                .build();
        //System.out.println(String.valueOf(userDetails.getAuthorities()));
        System.out.println("roles" + principal.getAuthorities());

        return new UsernamePasswordAuthenticationToken(
                principal, password, principal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
