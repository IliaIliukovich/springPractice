package com.telran.springpractice.service;

import com.telran.springpractice.entity.Account;
import com.telran.springpractice.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public Account create(Account account){
        return repository.save(account);
    }
}
