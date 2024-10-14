package com.example.banking.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String message) {
        super(message);
    }
}