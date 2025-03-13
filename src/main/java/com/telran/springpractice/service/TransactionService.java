package com.telran.springpractice.service;

import com.telran.springpractice.entity.Account;
import com.telran.springpractice.entity.Transaction;
import com.telran.springpractice.entity.enums.TransactionStatus;
import com.telran.springpractice.entity.enums.TransactionType;
import com.telran.springpractice.exception.AccountNotFoundException;
import com.telran.springpractice.exception.NotEnoughAmountException;
import com.telran.springpractice.repository.AccountRepository;
import com.telran.springpractice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    private final AccountRepository accountRepository;

    @Autowired
    public TransactionService(TransactionRepository repository, AccountRepository accountRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
    }

    public List<Transaction> getAll() {
        return repository.findAll();
    }

    public Transaction create(Transaction transaction) {
        return repository.save(transaction);
    }

    public List<Transaction> searchByType(TransactionType type, BigDecimal minAmount) {
        return repository.findByTypeAndAmountGreaterThanEqual(type, minAmount);
    }

    public Transaction refundTransactionById(String id) {
        Optional<Transaction> transactionOption = repository.findById(id);
        if (transactionOption.isPresent()) {
            Transaction transaction = transactionOption.get();
            return repository.save(new Transaction(
                    null, transaction.getType(), transaction.getAmount(), transaction.getDescription(), TransactionStatus.NEW,
                    transaction.getToAccountId(), transaction.getFromAccountId()
            ));
        }
        return null;
    }

    public Transaction transfer(Long from, Long to, BigDecimal amount) {
        Optional<Account> fromAccount = accountRepository.findById(from);
        Optional<Account> toAccount = accountRepository.findById(to);
        if (!fromAccount.isPresent() || !toAccount.isPresent()) {
            throw new AccountNotFoundException("Account not found");
        }
        Account sender = fromAccount.get();
        if (sender.getBalance().compareTo(amount) < 0) {
            throw new NotEnoughAmountException("Not enough amount");
        }
        Transaction transaction = new Transaction(null, TransactionType.CASH, amount, "new Transaction", TransactionStatus.NEW, from, to);
        sender.setBalance(sender.getBalance().subtract(amount));
        accountRepository.save(sender);

        Account receiver = toAccount.get();
        receiver.setBalance(receiver.getBalance().add(amount));
        accountRepository.save(receiver);

        return repository.save(transaction);
    }
}
