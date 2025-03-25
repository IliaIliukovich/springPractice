package com.telran.springpractice.service;

import com.telran.springpractice.dto.AccountSummaryDto;
import com.telran.springpractice.entity.Account;
import com.telran.springpractice.entity.Transaction;
import com.telran.springpractice.entity.enums.AccountStatus;
import com.telran.springpractice.entity.enums.AccountType;
import com.telran.springpractice.entity.enums.CurrencyCode;
import com.telran.springpractice.exception.AccountNotFoundException;
import com.telran.springpractice.repository.AccountRepository;
import com.telran.springpractice.repository.ClientRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository repository;

    private  final TransactionService transactionService;
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public AccountService(AccountRepository repository, TransactionService transactionService, AccountRepository accountRepository, ClientRepository clientRepository) {
        this.repository = repository;
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    public List<Account> getAll() {
        return repository.findAll();
    }


    public List<Account> filterByBalance(BigDecimal minValue, BigDecimal maxValue) {
        return repository.findAccountsByBalanceBetween(minValue, maxValue);
    }

    public Account getAccountById(Long id) {
        return repository.findById(id).get();
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
        Account account;
        account = new Account(
                null,
                "Standard",
                AccountType.DEBIT_CARD,
                AccountStatus.ACTIVE,
                new BigDecimal("0"),
                currencyCode,clientRepository.findById(clientId).get(),
               null,
                null);


        return repository.save(account);
    }

    public AccountSummaryDto accountSummaryDto(Long id){
        Optional <Account> accountOptional = accountRepository.findById(id);
        if(accountOptional.isPresent()){
            Account account = accountOptional.get();
            int from = account.getFromTransactions().size();
            int to = account.getToTransactions().size();
            int transactionsQuantity = (from + to);

            BigDecimal totalAmount = account.getFromTransactions().stream()
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalAmountTwo = account.getToTransactions().stream()
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

           return new AccountSummaryDto(transactionsQuantity, totalAmount, totalAmountTwo);
        }
       throw  new AccountNotFoundException("Account not Found!!");
    }

}
