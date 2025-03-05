package com.telran.springpractice.entity;

import com.telran.springpractice.entity.enums.TransactionStatus;
import com.telran.springpractice.entity.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Transaction {

    private String id;

    private TransactionType type;

    private BigDecimal amount;

    private String description;

    private TransactionStatus status;

    private Long fromAccountId;// получатель

    private Long toAccountId;// отправитель
}
