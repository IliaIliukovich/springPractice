package com.telran.springpractice.service;

import com.telran.springpractice.entity.Account;
import com.telran.springpractice.entity.enums.AccountStatus;
import com.telran.springpractice.entity.enums.AccountType;
import com.telran.springpractice.entity.enums.CurrencyCode;
import com.telran.springpractice.repository.AccountRepository;
import com.telran.springpractice.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    private final AccountRepository repository;

    private final ClientRepository clientRepository;

    @Autowired
    public AccountService(AccountRepository repository, ClientRepository clientRepository) {
        this.repository = repository;
        this.clientRepository = clientRepository;
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
                currencyCode, clientRepository.findById(clientId).get());

        return repository.save(account);
    }

}
