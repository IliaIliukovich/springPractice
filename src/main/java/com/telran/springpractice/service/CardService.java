package com.telran.springpractice.service;

import com.telran.springpractice.dto.CardCreateRequestDto;
import com.telran.springpractice.dto.CardCreateResponseDto;
import com.telran.springpractice.entity.Account;
import com.telran.springpractice.entity.Card;
import com.telran.springpractice.entity.Client;
import com.telran.springpractice.entity.enums.AccountType;
import com.telran.springpractice.exception.AccountNotFoundException;
import com.telran.springpractice.exception.ClientNotFoundException;
import com.telran.springpractice.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    public Card createCard(CardCreateRequestDto cardCreateRequestDto) {
        String uuid = UUID.randomUUID().toString();
        Optional<Client> clientById = clientService.getClientById(cardCreateRequestDto.clientId());
        if (clientById.isEmpty()) {
            throw new ClientNotFoundException("Client not found");
        }
        Client client = clientById.get();
       // accountService.create(new Account(null, AccountType.DEBIT_CARD, "LOAN", BLOCKED, 4000, 'RUB', 'b03dbcfc-d047-49a7-acbb-f3b1329e1fee'))
       // accountService.getAccountById();
       // new Card(null, cardCreateRequestDto.cardType(), uuid,)
        return null;
    }

}
