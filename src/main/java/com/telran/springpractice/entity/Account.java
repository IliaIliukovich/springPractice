package com.telran.springpractice.entity;

import com.telran.springpractice.entity.enums.AccountStatus;
import com.telran.springpractice.entity.enums.AccountType;
import com.telran.springpractice.entity.enums.CurrencyCode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Account {

    @Id
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyCode;
    private String clientId;

}
