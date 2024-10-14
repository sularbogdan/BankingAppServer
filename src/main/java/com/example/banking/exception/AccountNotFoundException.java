package com.example.banking.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String message) {
        super(message);
    }
}
