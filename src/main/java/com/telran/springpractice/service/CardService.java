package com.telran.springpractice.service;

import com.telran.springpractice.entity.Account;
import com.telran.springpractice.entity.Card;
import com.telran.springpractice.entity.Client;
import com.telran.springpractice.entity.enums.CardType;
import com.telran.springpractice.entity.enums.CurrencyCode;
import com.telran.springpractice.repository.CardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class CardService {

    private final CardRepository cardRepository;

    private final ClientService clientService;

    private final AccountService accountService;

    @Autowired
    public CardService(CardRepository cardRepository, ClientService clientService, AccountService accountService) {
        this.cardRepository = cardRepository;
        this.clientService = clientService;
        this.accountService = accountService;
    }

    private String generateCardHolderName(String clientId) {
        Client client = clientService.getClientById(clientId).orElseThrow(
                () -> new EntityNotFoundException("Client not found"));
        return client.getFirstName() + " " + client.getLastName();
    }

    private int generateCvv() {
        String cvv = String.format("%03d", new Random().nextInt(1000));
        return Integer.parseInt(cvv);
//        return new Random().nextInt(900) + 100;
    }

    private String generateCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }

        return cardNumber.toString();
    }

    private String generateExpiryDate() {
        LocalDate localDate = LocalDate.now().plusYears(2);
        StringBuilder sb = new StringBuilder();
        return sb.append(localDate.getMonthValue())
                .append("/")
                .append(localDate.getYear())
                .toString();
    }

    public void print() {
        System.out.println("generateCardHolderName " + generateCardHolderName("b2c2e8dd-6bce-4401-bd71-ffaed9d6ada0"));
        System.out.println("generateCvv " + generateCvv());
        System.out.println("generateCardNumber " + generateCardNumber());
        System.out.println("generateExpiryDate " + generateExpiryDate());
    }

    public Card save(String clientId, CardType cardType, CurrencyCode currency) {
        Account account = accountService.addDebitByClientId(clientId, currency);
        return cardRepository.save(new Card(null, cardType, generateCardNumber()
                , generateCardHolderName(clientId), generateCvv(), generateExpiryDate(), account));
    }

    public List<Card> getAll() {
        return cardRepository.findAll();
    }
}
