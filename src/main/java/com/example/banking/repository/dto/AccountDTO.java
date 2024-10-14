package com.example.banking.repository.dto;

import lombok.Data;


@Data
public class AccountDTO {
    private Long id;
    private String name;
    private Long cardNumber;
    private double balance;
    private String password;


}
