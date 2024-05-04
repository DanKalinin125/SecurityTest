package com.example.SecurityTest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardDTO {
    Long id;
    String title;
    String description;
}
