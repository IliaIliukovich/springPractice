package com.telran.springpractice.entity;

import com.telran.springpractice.entity.enums.AccountStatus;
import com.telran.springpractice.entity.enums.AccountType;
import com.telran.springpractice.entity.enums.CurrencyCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fromAccount")
    private List<Transaction> fromTransactions = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "toAccount")
    private List<Transaction> toTransactions = new ArrayList<>();

}
