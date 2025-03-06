package com.telran.springpractice.controller;

import com.telran.springpractice.entity.Transaction;
import com.telran.springpractice.entity.enums.TransactionStatus;
import com.telran.springpractice.entity.enums.TransactionType;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private List<Transaction> transactions = new ArrayList<>();

    @PostConstruct
    public void init() {
        transactions.add(Transaction.builder().id("1234").type(TransactionType.CASH).amount(new BigDecimal("45"))
                .description("text").fromAccountId(123L).toAccountId(456L).status(TransactionStatus.NEW).build());
        transactions.add(Transaction.builder().id("5678").type(TransactionType.CASH).amount(new BigDecimal("89"))
                .description("text").fromAccountId(89L).toAccountId(100L).status(TransactionStatus.APPROVED).build());
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
        Predicate<Transaction> predicate = t -> (type == null || (type == t.getType())) &&
                (minAmount == null || t.getAmount().compareTo(minAmount) > 0);
        return transactions.stream().filter(predicate).toList();
    }




}




