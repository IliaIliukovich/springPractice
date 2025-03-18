package com.telran.springpractice.service;

import com.telran.springpractice.entity.Client;
import com.telran.springpractice.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Client> getClientById(String uuid) {
        return repository.findById(uuid);
    }

    public List<Client> findByName(String name) {
        return repository.findByFirstName(name);
    }

    public List<Client> findBySurnameAndAddress(String surnameChar, String address) {
        return repository.findByLastNameStartingWithAndAddressContaining(surnameChar, address);
    }

    public Client addClient(Client client) {
        return repository.save(client);
    }

    @Transactional
    public Optional<Client> updateClient(Client client) {
        Optional<Client> found = repository.findById(client.getId());
        if (found.isPresent()) {
            return Optional.of(repository.save(client));
        }
        return Optional.empty();
    }

    public Optional<Client> changeStatus(String id, String status) {
        return null;
    }

    public boolean patchClient(String id, String address) {
        return false;
    }

    public int updateNullAddress() {
        return 0;
    }

    public void deleteClient(String id) {

    }

    @Transactional
    public boolean deleteAllInactive() {
        int result = repository.customQuery();
        return result != 0;
    }
}
