package com.example.SecurityTest.security;

import com.example.SecurityTest.repository.MyUserRepository;
import com.example.SecurityTest.security.jaas.MyAuthorityGranter;
import com.example.SecurityTest.security.jaas.MyLoginModule;
import com.example.SecurityTest.security.provider.MyAuthenticationProvider;
import com.example.SecurityTest.security.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.jaas.AbstractJaasAuthenticationProvider;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.authentication.jaas.DefaultJaasAuthenticationProvider;
import org.springframework.security.authentication.jaas.JaasAuthenticationProvider;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.security.auth.login.AppConfigurationEntry;
import java.util.Map;

import static javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag.REQUIRED;

@Configuration
@RequiredArgsConstructor
public class MyApplicationSecurityConfig {
    // Основная конфигурация приложения
    @Autowired
    private MyUserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    // Конфигурация JAAS

    @Bean
    public javax.security.auth.login.Configuration configuration() {
        final var configurationEntries =
                new AppConfigurationEntry[] {
                        new AppConfigurationEntry(
                                MyLoginModule.class.getCanonicalName(),
                                REQUIRED,
                                Map.of("userDetailsService", userDetailsService(), "passwordEncoder", passwordEncoder()))
                };
        return new InMemoryConfiguration(Map.of("SPRINGSECURITY", configurationEntries));
    }

    @Bean
    public AuthorityGranter authorityGranter(){
        MyAuthorityGranter myAuthorityGranter = new MyAuthorityGranter();
        myAuthorityGranter.setUserRepository(userRepository);
        return myAuthorityGranter;
    }
    @Bean
    public AbstractJaasAuthenticationProvider jaasAuthenticationProvider() {
        javax.security.auth.login.Configuration configuration = configuration();

        DefaultJaasAuthenticationProvider defaultJaasAuthenticationProvider = new DefaultJaasAuthenticationProvider();
        defaultJaasAuthenticationProvider.setConfiguration(configuration);
        defaultJaasAuthenticationProvider.setAuthorityGranters(new AuthorityGranter[]{authorityGranter()});

        return defaultJaasAuthenticationProvider;
    }

    // Бин для конфигурации Spring Security
    @Bean
    public AuthenticationProvider authenticationProvider() {
//        MyAuthenticationProvider authProvider = new MyAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;

        return jaasAuthenticationProvider();
    }


}
