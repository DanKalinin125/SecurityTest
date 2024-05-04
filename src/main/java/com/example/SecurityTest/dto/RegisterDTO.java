package com.example.SecurityTest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterDTO {
    private String username;
    private String email;
    private String password;
}
