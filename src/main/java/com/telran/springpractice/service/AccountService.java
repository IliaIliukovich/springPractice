package com.telran.springpractice.service;

import com.telran.springpractice.entity.Account;
import com.telran.springpractice.entity.Transaction;
import com.telran.springpractice.entity.enums.AccountStatus;
import com.telran.springpractice.entity.enums.AccountType;
import com.telran.springpractice.entity.enums.CurrencyCode;
import com.telran.springpractice.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository repository;

    @Autowired
    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public List<Account> getAll() {
        return repository.findAll();
    }

    public List<Account> filterByBalance(BigDecimal minValue, BigDecimal maxValue) {
        return repository.findAccountsByBalanceBetween(minValue, maxValue);
    }

    @Transactional
    public int deleteInactive() {
        return repository.removeAccountsByStatusINACTIVE();
    }

    @Transactional
    public Account create(Account account){
        return repository.save(account);
    }

    //типа DEBIT_CARD для заданного клиента в заданной валюте
    public Account addDebitByClientId(String clientId, CurrencyCode currencyCode){
        Account account = new Account(
                null,
                "Standard",
                AccountType.DEBIT_CARD,
                AccountStatus.ACTIVE,
                new BigDecimal("0"),
                currencyCode,
                clientId,List.of(),new ArrayList<Transaction>());

        return repository.save(account);
    }
}
