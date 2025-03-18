package com.telran.springpractice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telran.springpractice.client.CurrencyApiClient;
import com.telran.springpractice.dto.ExchangeRatesResponse;
import com.telran.springpractice.entity.enums.CurrencyCode;
import com.telran.springpractice.exception.ExchangeRateNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public BigDecimal convertAmountToRequiredCurrency(BigDecimal amount, CurrencyCode senderCurrencyCode, CurrencyCode receiverCurrencyCode) {
        if (senderCurrencyCode.equals(receiverCurrencyCode)) return amount;
        BigDecimal bankFee = new BigDecimal("0.98");

        Map<String, BigDecimal> currencyRates;
        try {
            currencyRates = getRates();
        } catch (IOException e) {
            throw new ExchangeRateNotFoundException("No exchange rate found for receiver currency: " + receiverCurrencyCode);
        }
        BigDecimal resultCurrencyAmount = currencyRates.get(senderCurrencyCode.name());
        if (senderCurrencyCode != CurrencyCode.USD) {
            resultCurrencyAmount = amount.divide(resultCurrencyAmount, 10, RoundingMode.HALF_UP);
        }
        if (receiverCurrencyCode != CurrencyCode.USD) {
            resultCurrencyAmount = amount.multiply(currencyRates.get(receiverCurrencyCode.name()));
        }
        resultCurrencyAmount = resultCurrencyAmount.multiply(bankFee);
        return resultCurrencyAmount.setScale(2, RoundingMode.DOWN);
    }
}
