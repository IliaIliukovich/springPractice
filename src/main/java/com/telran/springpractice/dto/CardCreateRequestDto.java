package com.telran.springpractice.dto;

public record CardCreateRequestDto(String clientId,
                                   String cardType,
                                   String currency) {
}
