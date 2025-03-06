package com.telran.springpractice.service;

import com.telran.springpractice.entity.Account;
import com.telran.springpractice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository repository;

    @Autowired
    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public List<Account> getAll(){
        return repository.findAll();
    }
}
