package com.telran.springpractice.controller;

import com.telran.springpractice.entity.Transaction;
import com.telran.springpractice.entity.enums.TransactionStatus;
import com.telran.springpractice.entity.enums.TransactionType;
import com.telran.springpractice.exception.AccountNotFoundException;
import com.telran.springpractice.exception.NotEnoughAmountException;
import com.telran.springpractice.service.TransactionService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final TransactionService service;

    @Autowired
    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Transaction> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody Transaction transaction) {
        service.create(transaction);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("/searchByType")
    public List<Transaction> searchByType(@RequestParam(required = false) TransactionType type,
                                          @RequestParam(required = false) BigDecimal minAmount) {

        return service.searchByType(type, minAmount);
    }

    @PostMapping("/refund/{id}")
    public ResponseEntity<Transaction> refundByID(@PathVariable String id) {
        Transaction refundTransactionById = service.refundTransactionById(id);
        if (refundTransactionById != null) {
            return new ResponseEntity<>(refundTransactionById, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(@RequestParam Long from, @RequestParam Long to, @RequestParam BigDecimal amount) {
        try {
            Transaction transfer = service.transfer(from, to, amount);
            return new ResponseEntity<>(transfer, HttpStatus.OK);
        } catch (AccountNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (NotEnoughAmountException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}




