package com.telran.springpractice.exception;

public class NotEnoughAmountException extends RuntimeException {

    public NotEnoughAmountException(String message) {
        super(message);
    }
}
