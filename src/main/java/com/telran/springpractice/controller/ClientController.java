package com.telran.springpractice.controller;

import com.telran.springpractice.dto.ClientStatisticDto;
import com.telran.springpractice.entity.Client;
import com.telran.springpractice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("{uuid}")
    public Optional<Client> getClientById(@PathVariable String uuid) {
        return service.getClientById(uuid);
    }

    @GetMapping("/search")
    public List<Client> findByName(@RequestParam String name) {
        return service.findByName(name);
    }

    @GetMapping("searchBySurnameAndAddress")
    public List<Client> findBySurnameAndAddress(@RequestParam String surnameChar, @RequestParam String address) {
        return service.findBySurnameAndAddress(surnameChar, address);
    }

    @PostMapping
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        Client added = service.addClient(client);
        return new ResponseEntity<>(added, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Client> updateClient(@RequestBody Client client) {
        Optional<Client> optional = service.updateClient(client);
        return optional.map(value -> new ResponseEntity<>(value, HttpStatus.ACCEPTED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping
    public ResponseEntity<Client> changeStatus(@RequestParam String id, @RequestParam(required = false) String status){
        Optional<Client> optional = service.changeStatus(id, status);
        return optional.map(client -> new ResponseEntity<>(client, HttpStatus.ACCEPTED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/patch")
    public ResponseEntity<Void> patchClient(@RequestParam String id, @RequestParam String address) {
        boolean isPatched = service.patchClient(id, address);
        return isPatched ? new ResponseEntity<>(HttpStatus.ACCEPTED) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/updateNullAddress")
    public ResponseEntity<String> updateNullAddress() {
        int count = service.updateNullAddress();
        return new ResponseEntity<>(count + " addresses replaced on 'Not provided'", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable String id) {
        service.deleteClient(id);
        return ResponseEntity.accepted().build();
    }


    @DeleteMapping("/deleteAllInactive")
    public ResponseEntity<String> deleteAllInactive() {
        boolean res = service.deleteAllInactive();
        return res ? new ResponseEntity<>("All clients with status 'INACTIVE' deleted successfully", HttpStatus.ACCEPTED) :
                new ResponseEntity<>("No clients with status 'INACTIVE' found", HttpStatus.ACCEPTED);
    }

    @GetMapping("/summary/{id}")
    public ResponseEntity<ClientStatisticDto> getSummary(@PathVariable String id) {
        return new ResponseEntity<>(service.getClientStatistic(id), HttpStatus.OK);
    }
}
