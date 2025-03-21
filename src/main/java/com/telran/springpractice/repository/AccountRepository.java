package com.telran.springpractice.repository;

import com.telran.springpractice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAccountsByBalanceBetween(BigDecimal minValue, BigDecimal maxValue);

    @Modifying
    @Query("DELETE FROM Account a Where a.status = 'INACTIVE'")
    int removeAccountsByStatusINACTIVE();

    @Modifying
    @Query("INSERT INTO Account (clientId, type, currencyCode, balance) VALUES (:clientId, 'DEBIT_CARD', :currency, 0)")
    void createDebitCardAccount(@Param("clientId") Long clientId, @Param("currency") String currency);

}
