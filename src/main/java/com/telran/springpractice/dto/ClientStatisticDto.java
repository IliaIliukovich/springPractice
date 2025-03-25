package com.telran.springpractice.dto;

import com.telran.springpractice.entity.enums.CurrencyCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ClientStatisticDto {

    private BigDecimal generalSum;

    private Map<CurrencyCode, BigDecimal> allAccountBalance;

    public ClientStatisticDto(BigDecimal generalSum, Map<CurrencyCode, BigDecimal> allAccountBalance) {
        this.generalSum = generalSum;
        this.allAccountBalance = allAccountBalance;
    }
}
