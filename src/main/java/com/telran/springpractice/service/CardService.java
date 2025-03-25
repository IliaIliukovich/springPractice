package com.telran.springpractice.service;

import com.telran.springpractice.dto.CardCreateRequestDto;
import com.telran.springpractice.entity.Account;
import com.telran.springpractice.entity.Card;
import com.telran.springpractice.entity.Client;
import com.telran.springpractice.entity.enums.AccountStatus;
import com.telran.springpractice.entity.enums.AccountType;
import com.telran.springpractice.entity.enums.CardType;
import com.telran.springpractice.entity.enums.CurrencyCode;
import com.telran.springpractice.exception.ClientNotFoundException;
import com.telran.springpractice.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Transactional
    public Card createCard(CardCreateRequestDto cardCreateRequestDto) {
        String uuid = createCardNumber(CardType.valueOf(cardCreateRequestDto.cardType()));
        Optional<Client> clientById = clientService.getClientById(cardCreateRequestDto.clientId());
        if (clientById.isEmpty()) {
            throw new ClientNotFoundException("Client not found");
        }
        Client client = clientById.get();
        Random random = new Random();
        int cvv =100 + random.nextInt(900);
        String expiryDate = LocalDate.now().plusDays(730).toString();

        Account account = new Account(null, "DEBIT", AccountType.DEBIT_CARD, AccountStatus.ACTIVE,
                new BigDecimal("0"), CurrencyCode.valueOf(cardCreateRequestDto.currency()), client, null, null, null);

        Card card = new Card(null, CardType.valueOf(cardCreateRequestDto.cardType()), uuid,
                client.getFirstName() + " " + client.getLastName(), cvv, expiryDate, account);
        Card createdCard = repository.save(card);
        account.setCard(createdCard);
        Account accountCreated = accountService.create(account);
        client.getAccount().add(accountCreated);
        clientService.addClient(client);
        return createdCard;
    }

    public static String createCardNumber(CardType cardType) {
        String bin = null;
        if (cardType.equals(CardType.VISA)) {
             bin = "411111";
        }else {
            bin = "510510";
        }

        StringBuilder cardNumber = new StringBuilder(bin);
        Random random = new Random();

        for (int i = 0; i < 9; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }

}
