package com.example.SecurityTest;

import com.example.SecurityTest.entity.MyRole;
import com.example.SecurityTest.repository.MyRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class OnApplicationReady {

    @Autowired
    private MyRoleRepository myRoleRepository;
    private final String[] ROLES = {"ROLE_USER", "ROLE_ADMIN"};

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        List<MyRole> alreadyInitMyRoles = myRoleRepository.findAll();
        for (String roleName : ROLES){
            boolean containedFlag = false;
            for (MyRole myRole : alreadyInitMyRoles){
                if (Objects.equals(myRole.getName(), roleName)){
                    containedFlag = true;
                    break;
                }
            }
            if (!containedFlag){
                myRoleRepository.save(MyRole.builder().name(roleName).build());
            }
        }
    }
}