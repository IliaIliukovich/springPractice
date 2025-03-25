package com.telran.springpractice.controller;

import com.telran.springpractice.dto.CardCreateRequestDto;
import com.telran.springpractice.entity.Card;
import com.telran.springpractice.repository.CardRepository;
import com.telran.springpractice.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
public class CardController {

    private final CardService service;

    public CardController(CardService service) {
        this.service = service;
    }

    @PostMapping("/new")
    public ResponseEntity<Card> createCard(@RequestBody CardCreateRequestDto cardDto) {
        Card card = service.createCard(cardDto);
        return new ResponseEntity<>(card, HttpStatus.CREATED);
    }
}
