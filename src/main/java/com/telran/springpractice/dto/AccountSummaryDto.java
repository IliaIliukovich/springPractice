package com.telran.springpractice.dto;

import java.math.BigDecimal;

public record AccountSummaryDto(int transactionQuantity,
                                BigDecimal expenses,
                                BigDecimal income) {
}
