package com.telran.springpractice.entity;

import com.telran.springpractice.entity.enums.TransactionStatus;
import com.telran.springpractice.entity.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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
