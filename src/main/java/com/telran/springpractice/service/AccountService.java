package com.telran.springpractice.service;

import com.telran.springpractice.dto.AccountSummaryDto;
import com.telran.springpractice.entity.Account;
import com.telran.springpractice.entity.Client;
import com.telran.springpractice.entity.Transaction;
import com.telran.springpractice.entity.enums.AccountStatus;
import com.telran.springpractice.entity.enums.AccountType;
import com.telran.springpractice.entity.enums.CurrencyCode;
import com.telran.springpractice.exception.ResourceNotFoundException;
import com.telran.springpractice.repository.AccountRepository;
import com.telran.springpractice.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
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

    @Transactional
    public Account addDebitByClientId(String clientId, CurrencyCode currencyCode){
        Account account;
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client with id = " + clientId + " not found"));
        account = new Account(
                null,
                "Debit card account",
                AccountType.DEBIT_CARD,
                AccountStatus.ACTIVE,
                new BigDecimal("0"),
                currencyCode,
                client,
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

            BigDecimal totalAmount = account.getFromTransactions().stream() // TODO different currencies here
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalAmountTwo = account.getToTransactions().stream() // TODO different currencies here
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

           return new AccountSummaryDto(transactionsQuantity, totalAmount, totalAmountTwo);
        }
       throw  new ResourceNotFoundException("Account not found!");
    }

}
