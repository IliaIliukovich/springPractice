package com.telran.springpractice.service;

import com.telran.springpractice.dto.ClientStatisticDto;
import com.telran.springpractice.entity.Account;
import com.telran.springpractice.entity.Client;
import com.telran.springpractice.entity.enums.CurrencyCode;
import com.telran.springpractice.exception.ResourceNotFoundException;
import com.telran.springpractice.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ClientService {

    private final ClientRepository repository;

    private CurrencyService currencyService;

    @Autowired
    public ClientService(ClientRepository repository, CurrencyService currencyService) {

        this.repository = repository;
        this.currencyService = currencyService;
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

    @Transactional
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

    @Transactional
    public Optional<Client> changeStatus(String id, String status) {
        return null;
    }

    @Transactional
    public boolean patchClient(String id, String address) {
        return false;
    }

    @Transactional
    public int updateNullAddress() {
        return 0;
    }

    @Transactional
    public void deleteClient(String id) {

    }

    @Transactional
    public boolean deleteAllInactive() {
        int result = repository.customQuery();
        return result != 0;
    }

    public ClientStatisticDto getClientStatistic(String id) {
        Client client = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client with id = " + id + " not found"));
        BigDecimal generalSum = getGeneralSum(client);
        Map<CurrencyCode, BigDecimal> balanceByCurrency = getBalanceByCurrency(client);
        return new ClientStatisticDto(generalSum, balanceByCurrency);
    }

    private BigDecimal getGeneralSum(Client client) {
        Map<String, BigDecimal> currencyRates = currencyService.getRates();
        BigDecimal generalSum = client.getAccounts().stream().map(a -> currencyService
                .convertAmountToRequiredCurrency(a.getBalance(),a.getCurrencyCode(), CurrencyCode.USD, currencyRates))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return generalSum;
    }

    private static Map<CurrencyCode, BigDecimal> getBalanceByCurrency(Client client) {
        Map<CurrencyCode, BigDecimal> balanceByCurrency = client.getAccounts().stream()
                .collect(Collectors.groupingBy(Account::getCurrencyCode,
                        Collectors.mapping(Account::getBalance, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        return balanceByCurrency;
    }
}
