package com.telran.springpractice.dto;

import java.math.BigDecimal;

public record AccountSummaryDto(int transactionsQuantity, BigDecimal expenses, BigDecimal income) {
}
