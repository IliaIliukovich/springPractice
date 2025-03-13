package com.telran.springpractice.repository;

import com.telran.springpractice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAccountsByBalanceBetween(BigDecimal minValue, BigDecimal maxValue);
}
