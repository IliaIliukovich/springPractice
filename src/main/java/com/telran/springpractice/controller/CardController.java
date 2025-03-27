package com.telran.springpractice.controller;

import com.telran.springpractice.entity.Card;
import com.telran.springpractice.entity.enums.CardType;
import com.telran.springpractice.entity.enums.CurrencyCode;
import com.telran.springpractice.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {

    private CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<Card> create(@RequestParam String clientId,
                                       @RequestParam CardType cardType, @RequestParam CurrencyCode currency){
        Card returnedCard = cardService.save(clientId, cardType, currency);
        return new ResponseEntity<>(returnedCard, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Card>> getAll(){
        return new ResponseEntity<>(cardService.getAll(),HttpStatus.OK);
    }
}
