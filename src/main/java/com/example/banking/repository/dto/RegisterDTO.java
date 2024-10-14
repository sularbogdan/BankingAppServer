package com.example.banking.repository.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String name;
    private Long cardNumber;
    private String password;
}
