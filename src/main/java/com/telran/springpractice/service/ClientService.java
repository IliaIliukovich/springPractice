package com.telran.springpractice.service;

import com.telran.springpractice.entity.Client;
import com.telran.springpractice.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository repository;

    @Autowired
    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public List<Client> getAll(){
        return repository.findAll();
    }
}
