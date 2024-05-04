package com.example.SecurityTest.controller;

import com.example.SecurityTest.dto.CardDTO;
import com.example.SecurityTest.service.CardService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/card")
@AllArgsConstructor
public class CardController {
    CardService cardService;

    @GetMapping
    public ResponseEntity<List<CardDTO>> getAll() {
        return new ResponseEntity<>(cardService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<?> getCurrent(@PathVariable Long cardId) {
        Map<String, String> response = new HashMap<>();

        try {
            return new ResponseEntity<>(cardService.getCurrent(cardId), HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", "Такой карточки нет");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CardDTO cardDTO) {
        return new ResponseEntity<>(cardService.create(cardDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> delete(@PathVariable Long cardId) {
        cardService.delete(cardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
