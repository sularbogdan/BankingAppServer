package com.example.banking.repository.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String name;
    private Long cardNumber;
    private String password;
}
