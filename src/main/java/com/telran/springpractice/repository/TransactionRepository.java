package com.telran.springpractice.repository;

import com.telran.springpractice.entity.Transaction;
import com.telran.springpractice.entity.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {


    List<Transaction> findByTypeAndAmountGreaterThanEqual(TransactionType type, BigDecimal minAmount);

}
