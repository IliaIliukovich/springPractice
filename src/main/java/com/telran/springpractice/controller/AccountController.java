package com.telran.springpractice.controller;

import com.telran.springpractice.entity.Account;
import com.telran.springpractice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> getAll() {
        return accountService.getAll();
    }

    @GetMapping("/filter")
    public List<Account> filterByBalance(@RequestParam BigDecimal minValue, @RequestParam BigDecimal maxValue) {
        List<Account> accounts = accountService.filterByBalance(minValue, maxValue);
        return accounts;
    }

    @DeleteMapping("/delete-all-inactive")
    public ResponseEntity<String> deleteInactive() {
        int i = accountService.deleteInactive();
        return i != 0 ? new ResponseEntity<>("All inactive accounts are deleted successfully", HttpStatus.OK)
                : new ResponseEntity<>("No inactive accounts are found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add-debit-account")
    public ResponseEntity<Void> create(@RequestBody Account account) {
        Account returnedAccount = accountService.create(account);
        if (returnedAccount == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
