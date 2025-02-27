package com.telran.springpractice.controller;

import com.telran.springpractice.entity.Client;
import com.telran.springpractice.entity.ClientStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientController {

    List<Client> clients = new ArrayList<>();

    public ClientController() {
        // Добавление 10 клиентов через конструктор
        clients.add(new Client(UUID.randomUUID().toString(), "Schmidt", "Hans", "DE123456789", "h.schmidt@example.com", null, "+49 30 1234567", ClientStatus.ACTIVE));
        clients.add(new Client(UUID.randomUUID().toString(), "Müller", "Anna", "DE987654321", "a.mueller@example.com", "Munich, Germany", "+49 89 7654321", ClientStatus.INACTIVE));
        clients.add(new Client(UUID.randomUUID().toString(), "Klein", "Peter", "DE567890123", "p.klein@example.com", "Hamburg, Germany", "+49 40 6789012", ClientStatus.BLOCKED));
        clients.add(new Client(UUID.randomUUID().toString(), "Schneider", "Maria", "DE456789012", "m.schneider@example.com", "Frankfurt, Germany", "+49 69 1234567", ClientStatus.ACTIVE));
        clients.add(new Client(UUID.randomUUID().toString(), "Fischer", "Lukas", "DE234567890", "l.fischer@example.com", "Stuttgart, Germany", "+49 711 9876543", ClientStatus.INACTIVE));
        clients.add(new Client(UUID.randomUUID().toString(), "Weber", "Sophie", "DE890123456", "s.weber@example.com", "Cologne, Germany", "+49 221 4567890", ClientStatus.ACTIVE));
        clients.add(new Client(UUID.randomUUID().toString(), "Meyer", "Max", "DE345678901", "m.meyer@example.com", "Düsseldorf, Germany", "+49 211 2345678", ClientStatus.BLOCKED));
        clients.add(new Client(UUID.randomUUID().toString(), "Wagner", "Julia", "DE678901234", "j.wagner@example.com", "Dresden, Germany", "+49 351 8765432", ClientStatus.ACTIVE));
        clients.add(new Client(UUID.randomUUID().toString(), "Becker", "Anna", "DE789012345", "p.becker@example.com", "Leipzig, Germany", "+49 341 7654321", ClientStatus.INACTIVE));
        clients.add(new Client(UUID.randomUUID().toString(), "Hoffmann", "Clara", "DE012345678", "c.hoffmann@example.com", null, "+49 421 1234567", ClientStatus.ACTIVE));
    }

    @GetMapping
    public List<Client> getClients() {
        return clients;
    }

    @GetMapping("{uuid}")
    public Client getClientsByUUID(@PathVariable String uuid) {
        for (Client client : clients) {
            if (client.getId().equals(uuid)) {
                return client;
            }
        }
        return null;
    }

    @GetMapping("search")
    public List<Client> getClientsByName(@RequestParam String name) {
        ArrayList<Client> clientList = new ArrayList<>();
        for (Client client : clients) {
            if (client.getFirstName().toLowerCase().equals(name.toLowerCase())) {
                clientList.add(client);
            }
        }
        return clientList;
    }

    @GetMapping("searchByAddress")
    public List<Client> getSurnameAddressClients(@RequestParam String surnameChar, @RequestParam String address) {
        List<Client> result = new ArrayList<>();
        for (Client client : clients) {
            if (client.getLastName().startsWith(surnameChar) && client.getAddress().contains(address)) {
                result.add(client);
            }
        }
        return result;
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) {
        clients.add(client);
        return client;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable String id) {
        boolean res = clients.removeIf(client -> client.getId().equals(id));
        if (res) {
            return new ResponseEntity<>("Client with id " + id + " was deleted", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Client with id " + id + " was not deleted", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateClient(@RequestBody Client client) {
        for (Client c : clients) {
            if (c.getId().equals(client.getId())) {
                c.setFirstName(client.getFirstName());
                c.setLastName(client.getLastName());
                c.setEmail(client.getEmail());
                c.setPhone(client.getPhone());
                c.setAddress(client.getAddress());
                c.setStatus(client.getStatus());
                return new ResponseEntity<>("Client with id " + client.getId() + " updated successfully", HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>("Client with id " + client.getId() + " not found", HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/patch")
    public ResponseEntity<Void> patchClient(@RequestParam String id, @RequestParam String address) {
        for (Client c : clients) {
            if (c.getId().equals(id)) {
                c.setAddress(address);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteAllInactive")
    public ResponseEntity<String> deleteAllInactiveClients() {
        boolean res = clients.removeIf(client -> client.getStatus().equals(ClientStatus.INACTIVE));
        if (res) {
            return new ResponseEntity<>("All client with status \" + ClientStatus.INACTIVE + \" deleted successfully", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Delete error", HttpStatus.CONFLICT);
    }

    @PatchMapping("/whenAddressIsNull")
    public ResponseEntity<String> whenAddressIsNull() {
        int count =0;
        for (Client client : clients) {
            if (client.getAddress() == null) {
                client.setAddress("Not provided");
                count++;
            }
        }
        return new ResponseEntity<>(count + " Addresses replaced on 'Not provided'", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/updateAllStatus")
    public String updateAllStatus(@RequestParam ClientStatus status) {
        clients.forEach( client -> client.setStatus(status));
        return "All client status updated '" + status +"' successfully";
    }

}
