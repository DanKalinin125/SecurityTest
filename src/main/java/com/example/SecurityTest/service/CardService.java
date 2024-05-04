package com.example.SecurityTest.service;

import com.example.SecurityTest.dto.CardDTO;
import com.example.SecurityTest.entity.Card;
import com.example.SecurityTest.repository.CardRepository;
import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    public List<CardDTO> getAll() {
        List<CardDTO> cardDTOList = new ArrayList<>();
        for (Card card : cardRepository.findAll()) {
            cardDTOList.add(CardDTO.builder()
                    .id(card.getId())
                    .title(card.getTitle())
                    .description(card.getDescription())
                    .build());
        }
        return cardDTOList;
    }

    public CardDTO getCurrent(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new ObjectNotFoundException(cardId, "Карточка"));
        return CardDTO.builder()
                .id(card.getId())
                .title(card.getTitle())
                .description(card.getDescription())
                .build();
    }

    public CardDTO create(CardDTO cardDTO) throws ObjectNotFoundException {
        Card card = Card.builder()
                .title(cardDTO.getTitle())
                .description(cardDTO.getDescription())
                .build();
        card = cardRepository.save(card);
        return CardDTO.builder()
                .id(card.getId())
                .title(card.getTitle())
                .description(card.getDescription())
                .build();
    }

    public void delete(Long cardId) throws ObjectNotFoundException {
        cardRepository.findById(cardId).orElseThrow(() -> new ObjectNotFoundException(cardId, "Карточка"));
        cardRepository.deleteById(cardId);
    }
}
