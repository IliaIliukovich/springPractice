package com.telran.springpractice.service;

import com.telran.springpractice.entity.Transaction;
import com.telran.springpractice.entity.enums.TransactionStatus;
import com.telran.springpractice.entity.enums.TransactionType;
import com.telran.springpractice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    @Autowired
    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
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
}
