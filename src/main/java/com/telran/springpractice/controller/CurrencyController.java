package com.telran.springpractice.controller;

import com.telran.springpractice.service.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getCurrencyRates() {
        try {
            Map<String, BigDecimal> rates = currencyService.getRates();
            return ResponseEntity.ok(rates);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't get currencies from third party API", e);
        }
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleException(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
    }
}
