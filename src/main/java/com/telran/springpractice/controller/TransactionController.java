package com.telran.springpractice.controller;

//REST запрос на вывод списка всех переводов
//        - REST запрос на добавление перевода
//        - REST запрос на поиск перевода по типу и минимальной сумме
//        - REST запрос на перевод назад средств заданной транзакции

import com.telran.springpractice.entity.Client;
import com.telran.springpractice.entity.Transaction;
import com.telran.springpractice.entity.enums.TransactionStatus;
import com.telran.springpractice.entity.enums.TransactionType;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private List<Transaction> transactions = new ArrayList<>();

    @PostConstruct
    public void init() {
        transactions.add(Transaction.builder()
                .id("111")
                .type(TransactionType.CASH)
                .amount(new BigDecimal("100000"))
                .description("Add money")
                .fromAccountId(123L)
                .toAccountId(456L)
                .status(TransactionStatus.NEW)
                .build());

        transactions.add(Transaction.builder()
                .id("222")
                .type(TransactionType.TRANSFER)
                .amount(new BigDecimal("200000"))
                .description("Transfer money")
                .fromAccountId(123L)
                .toAccountId(456L)
                .status(TransactionStatus.PENDING)
                .build());
    }

    @GetMapping
    public List<Transaction> getAll() {
        return transactions;
    }

    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody Transaction transaction) {
        transactions.add(transaction);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("/searchByType")
    public List<Transaction> searchByType(@RequestParam(required = false) TransactionType type,
                                          @RequestParam(required = false) BigDecimal minAmount) {
        if (type == null && minAmount == null) {
            return transactions;
        } else if (type == null) {
            return transactions.stream()
                    .filter(t -> t.getAmount().compareTo(minAmount) > 0)
                    .toList();
        } else if (minAmount == null) {
            return transactions.stream().filter(t -> t.getType().equals(type))
                    .toList();
        } else {
            return transactions.stream().filter(t -> t.getType().equals(type))
                    .filter(t -> t.getAmount().compareTo(minAmount) > 0)
                    .toList();
        }
    }

    @PostMapping("/returnById")
    public ResponseEntity<Void> returnTransactionById(@RequestParam String id) {
        transactions.stream().filter(t -> t.getId().equals(id)).findFirst().ifPresent(t -> {
            create(Transaction.builder().id(idGenerate()).type(TransactionType.REFUND).amount(new BigDecimal(String.valueOf(t.getAmount())))
                    .description("REFUND by id " + t.getId()).fromAccountId(t.getToAccountId()).toAccountId(t.getFromAccountId()).status(TransactionStatus.NEW).build());
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public String idGenerate() {
        return UUID.randomUUID().toString();
    }
}

