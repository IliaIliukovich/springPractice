package com.telran.springpractice.service;

import com.telran.springpractice.entity.Account;
import com.telran.springpractice.entity.Transaction;
import com.telran.springpractice.entity.enums.TransactionStatus;
import com.telran.springpractice.entity.enums.TransactionType;
import com.telran.springpractice.exception.ResourceNotFoundException;
import com.telran.springpractice.exception.NotEnoughAmountException;
import com.telran.springpractice.repository.AccountRepository;
import com.telran.springpractice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TransactionService {

    private final TransactionRepository repository;

    private final AccountRepository accountRepository;

    private final CurrencyService currencyService;

    @Autowired
    public TransactionService(TransactionRepository repository, AccountRepository accountRepository, CurrencyService currencyService) {
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.currencyService = currencyService;
    }

    public List<Transaction> getAll() {
        return repository.findAll();
    }

    @Transactional
    public Transaction create(Transaction transaction) {
        return repository.save(transaction);
    }

    public List<Transaction> searchByType(TransactionType type, BigDecimal minAmount) {
        return repository.findByTypeAndAmountGreaterThanEqual(type, minAmount);
    }

    @Transactional
    public Transaction refundTransactionById(String id) {
        Optional<Transaction> transactionOption = repository.findById(id);
        if (transactionOption.isPresent()) {
            Transaction transaction = transactionOption.get();
            return repository.save(new Transaction(
                    null, transaction.getType(), transaction.getAmount(), transaction.getDescription(), TransactionStatus.NEW,
                    transaction.getCurrencyCode(), transaction.getToAccount(), transaction.getFromAccount()
            ));
        }
        return null;
    }

    @Transactional
    public Transaction transfer(Long from, Long to, BigDecimal amount) {
        Optional<Account> fromAccount = accountRepository.findById(from);
        Optional<Account> toAccount = accountRepository.findById(to);
        if (fromAccount.isEmpty() || toAccount.isEmpty()) {
            throw new ResourceNotFoundException("Account not found");
        }

        Account sender = fromAccount.get();
        Account receiver = toAccount.get();
        if (sender.getBalance().compareTo(amount) < 0) {
            throw new NotEnoughAmountException("Not enough amount");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        accountRepository.save(sender);

        Map<String, BigDecimal> currencyRates = currencyService.getRates();
        BigDecimal amountInRequiredCurrency = currencyService.convertAmountToRequiredCurrency(amount, sender.getCurrencyCode(),
                receiver.getCurrencyCode(), currencyRates);
        receiver.setBalance(receiver.getBalance().add(amountInRequiredCurrency));
        accountRepository.save(receiver);

        Transaction transaction = new Transaction(null, TransactionType.CASH, amount, "Transfer between accounts",
                TransactionStatus.NEW, sender.getCurrencyCode(),sender, receiver );
        return repository.save(transaction);
    }

}
