package com.telran.springpractice.controller;

import com.telran.springpractice.entity.Account;
import com.telran.springpractice.entity.enums.AccountStatus;
import com.telran.springpractice.entity.enums.AccountType;
import com.telran.springpractice.entity.enums.CurrencyCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    List<Account> accounts = new ArrayList<>();

    public AccountController() {
        accounts.add(new Account(1l, "Schmidt", AccountType.CHECKING, AccountStatus.ACTIVE, new BigDecimal("15432"), CurrencyCode.EUR, "123ABC"));
        accounts.add(new Account(2l, "Müller", AccountType.LOAN, AccountStatus.BLOCKED, new BigDecimal("8978"), CurrencyCode.GBP, "123CDE"));
        accounts.add(new Account(3l, "Klein", AccountType.DEBIT_CARD, AccountStatus.INACTIVE, new BigDecimal("94787"), CurrencyCode.USD, "543QWE"));
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAll() {
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Account>> filterByBalance(@RequestParam BigDecimal minValue, @RequestParam BigDecimal maxValue) {
        List<Account> result = accounts
                .stream()
                .filter(account -> account.getBalance().compareTo(minValue) >= 0 && account.getBalance().compareTo(maxValue) <= 0)
                .toList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete-all-inactive")
    public ResponseEntity<Void> deleteInactive() {
        accounts.removeIf(account -> account.getStatus() == AccountStatus.INACTIVE);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/add-debit-account")
    public ResponseEntity<Void> addDebitAccount(@RequestParam String id,@RequestParam CurrencyCode currencyCode) {
        accounts.add(new Account(4L, "Current Account", AccountType.DEBIT_CARD, AccountStatus.ACTIVE, new BigDecimal("0"), currencyCode, id));
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
