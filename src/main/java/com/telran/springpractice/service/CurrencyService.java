package com.telran.springpractice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telran.springpractice.client.CurrencyApiClient;
import com.telran.springpractice.dto.ExchangeRatesResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@Service
public class CurrencyService {

    private final CurrencyApiClient currencyApiClient;
    private final ObjectMapper objectMapper;

    public CurrencyService(CurrencyApiClient currencyApiClient, ObjectMapper objectMapper) {
        this.currencyApiClient = currencyApiClient;
        this.objectMapper = objectMapper;
    }

    public Map<String, BigDecimal> getRates() throws IOException {
        String json = currencyApiClient.getExchangeRates();
        ExchangeRatesResponse response = objectMapper.readValue(json, ExchangeRatesResponse.class);
        return response.getExchangeRates();
    }
}
