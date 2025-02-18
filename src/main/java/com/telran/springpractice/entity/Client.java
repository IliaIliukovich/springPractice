package com.telran.springpractice.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Client {

    private String id;
    private String lastName;
    private String firstName;
    private String taxCode;
    private String email;
    private String address;
    private String phone;
    private ClientStatus status;

}

