package com.telran.springpractice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.telran.springpractice.entity.enums.CurrencyCode;
import com.telran.springpractice.entity.enums.TransactionStatus;
import com.telran.springpractice.entity.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private BigDecimal amount;

    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

//    private Long fromAccountId;// получатель
//
//    private Long toAccountId;// отправитель

    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyCode;

    @ManyToOne
    @JsonIgnore
    private Account fromAccount;


    @ManyToOne
    @JsonIgnore
    private Account toAccount;

}
