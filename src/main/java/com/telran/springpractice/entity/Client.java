package com.telran.springpractice.entity;

import com.telran.springpractice.entity.enums.ClientStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToMany(mappedBy = "client")
    private List<Account> accounts;

    private String lastName;

    private String firstName;

    private String taxCode;

    private String email;

    private String address;

    private String phone;

    @Enumerated(EnumType.STRING)
    private ClientStatus status;
}

