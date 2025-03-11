package com.telran.springpractice.controller;

import com.telran.springpractice.entity.Account;
import com.telran.springpractice.entity.enums.AccountStatus;
import com.telran.springpractice.entity.enums.AccountType;
import com.telran.springpractice.entity.enums.CurrencyCode;
import com.telran.springpractice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private AccountService accountService;

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

//    @DeleteMapping("/delete-all-inactive")
//    public ResponseEntity<Void> deleteInactive() {
//        accounts.removeIf(account -> account.getStatus() == AccountStatus.INACTIVE);
//        return new ResponseEntity<>(HttpStatus.ACCEPTED);
//    }
//
//    @PostMapping("/add-debit-account")
//    public ResponseEntity<Void> addDebitAccount(@RequestParam String id,@RequestParam CurrencyCode currencyCode) {
//        accounts.add(new Account(4L, "Current Account", AccountType.DEBIT_CARD, AccountStatus.ACTIVE, new BigDecimal("0"), currencyCode, id));
//        return new ResponseEntity<Void>(HttpStatus.CREATED);
//    }
}
