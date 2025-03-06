package com.telran.springpractice.controller;

import com.telran.springpractice.entity.Client;
import com.telran.springpractice.entity.enums.ClientStatus;
import com.telran.springpractice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService service;

    @Autowired
    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<Client> getAll() {
        return service.getAll();
    }

//    @GetMapping("{uuid}")
//    public Optional<Client> getClientById(@PathVariable String uuid) {
//        return clients.stream().filter(client -> client.getId().equals(uuid)).findAny();
//    }
//
//    @GetMapping("/search")
//    public List<Client> findByName(@RequestParam String name) {
//        return clients.stream().filter(client -> client.getFirstName().equals(name)).toList();
//    }
//
//    @GetMapping("searchBySurnameAndAddress")
//    public List<Client> findBySurnameAndAddress(@RequestParam String surnameChar, @RequestParam String address) {
//        List<Client> result = new ArrayList<>();
//        for (Client client : clients) {
//            if (client.getLastName().startsWith(surnameChar) && client.getAddress().contains(address)) {
//                result.add(client);
//            }
//        }
//        return result;
//    }
//
//    @PostMapping
//    public ResponseEntity<Client> addClient(@RequestBody Client client) {
//        client.setId(UUID.randomUUID().toString());
//        clients.add(client);
//        return new ResponseEntity<>(client, HttpStatus.CREATED);
//    }
//
//    @PutMapping
//    public ResponseEntity<Client> updateClient(@RequestBody Client client) {
//        String id = client.getId();
//        Optional<Client> optional = clients.stream().filter(c -> c.getId().equals(id)).findAny();
//        if (optional.isPresent()) {
//            Client found = optional.get();
//            found.setEmail(client.getEmail());
//            found.setPhone(client.getPhone());
//            found.setAddress(client.getAddress());
//            found.setStatus(client.getStatus());
//            found.setFirstName(client.getFirstName());
//            found.setLastName(client.getLastName());
//            found.setTaxCode(client.getTaxCode());
//            return new ResponseEntity<>(found, HttpStatus.ACCEPTED);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @PatchMapping
//    public ResponseEntity<Client> changeStatus(@RequestParam String id, @RequestParam(required = false) String status){
//        Optional<Client> optional = clients.stream().filter(c -> c.getId().equals(id)).findAny();
//        if (optional.isPresent()) {
//            Client client = optional.get();
//            ClientStatus clientStatus = status == null ? ClientStatus.ACTIVE : ClientStatus.valueOf(status);
//            client.setStatus(clientStatus);
//            return new ResponseEntity<>(client, HttpStatus.ACCEPTED);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @PatchMapping("/patch")
//    public ResponseEntity<Void> patchClient(@RequestParam String id, @RequestParam String address) {
//        for (Client c : clients) {
//            if (c.getId().equals(id)) {
//                c.setAddress(address);
//                return new ResponseEntity<>(HttpStatus.ACCEPTED);
//            }
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @PatchMapping("/updateNullAddress")
//    public ResponseEntity<String> updateNullAddress() {
//        int count =0;
//        for (Client client : clients) {
//            if (client.getAddress() == null) {
//                client.setAddress("Not provided");
//                count++;
//            }
//        }
//        return new ResponseEntity<>(count + " addresses replaced on 'Not provided'", HttpStatus.ACCEPTED);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteClient(@PathVariable String id) {
//        clients.removeIf(client -> client.getId().equals(id));
//        return ResponseEntity.accepted().build();
//    }
//
//
//    @DeleteMapping("/deleteAllInactive")
//    public ResponseEntity<String> deleteAllInactive() {
//        boolean res = clients.removeIf(client -> client.getStatus().equals(ClientStatus.INACTIVE));
//        if (res) {
//            return new ResponseEntity<>("All clients with status 'INACTIVE' deleted successfully", HttpStatus.ACCEPTED);
//        }
//        return new ResponseEntity<>("No clients with status 'INACTIVE' found", HttpStatus.ACCEPTED);
//    }

}
