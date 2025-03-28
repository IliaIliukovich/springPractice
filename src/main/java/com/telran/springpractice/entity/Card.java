package com.telran.springpractice.entity;

import com.telran.springpractice.entity.enums.CardType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private String cardNumber;

    private String cardHolder;

    private Integer cvv;

    private String expiryDate;

    @OneToOne(cascade = CascadeType.ALL)
    private Account account;
}
