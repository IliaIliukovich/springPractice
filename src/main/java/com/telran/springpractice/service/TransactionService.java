package com.telran.springpractice.service;

import com.telran.springpractice.entity.Account;
import com.telran.springpractice.entity.Transaction;
import com.telran.springpractice.entity.enums.CurrencyCode;
import com.telran.springpractice.entity.enums.TransactionStatus;
import com.telran.springpractice.entity.enums.TransactionType;
import com.telran.springpractice.exception.AccountNotFoundException;
import com.telran.springpractice.exception.ExchangeRateNotFoundException;
import com.telran.springpractice.exception.NotEnoughAmountException;
import com.telran.springpractice.repository.AccountRepository;
import com.telran.springpractice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
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
        if (fromAccount.isEmpty() || toAccount.isEmpty()) {
            throw new AccountNotFoundException("Account not found");
        }

        Account sender = fromAccount.get();
        Account receiver = toAccount.get();
        if (sender.getBalance().compareTo(amount) < 0) {
            throw new NotEnoughAmountException("Not enough amount");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        accountRepository.save(sender);

        BigDecimal amountInRequiredCurrency = convertAmountToRequiredCurrency(amount, sender.getCurrencyCode(), receiver.getCurrencyCode());
        Transaction transaction = new Transaction(null, TransactionType.CASH, amountInRequiredCurrency, "new Transaction", TransactionStatus.NEW, from, to);

        receiver.setBalance(receiver.getBalance().add(amountInRequiredCurrency));
        accountRepository.save(receiver);

        return repository.save(transaction);
    }

    private BigDecimal convertAmountToRequiredCurrency(BigDecimal amount, CurrencyCode senderCurrencyCode, CurrencyCode receiverCurrencyCode) {
        if (senderCurrencyCode.equals(receiverCurrencyCode)) return amount;
        BigDecimal bankFee = new BigDecimal("0.98");

        Map<String, BigDecimal> currencyRates;
        try {
            currencyRates = currencyService.getRates();
        } catch (IOException e) {
            throw new ExchangeRateNotFoundException("No exchange rate found for receiver currency: " + receiverCurrencyCode);
        }
        BigDecimal resultCurrencyAmount = currencyRates.get(senderCurrencyCode.name());
        if (senderCurrencyCode != CurrencyCode.USD) {
            resultCurrencyAmount = amount.divide(resultCurrencyAmount, 10, RoundingMode.HALF_UP);
        }
        if (receiverCurrencyCode != CurrencyCode.USD) {
            resultCurrencyAmount = amount.multiply(currencyRates.get(receiverCurrencyCode.name()));
        }
        resultCurrencyAmount = resultCurrencyAmount.multiply(bankFee);
        return resultCurrencyAmount;
    }
}
