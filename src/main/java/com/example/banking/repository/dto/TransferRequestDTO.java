package com.example.banking.repository.dto;

import lombok.Data;

@Data
public class TransferRequestDTO {
    private Long sourceAccountId;
    private Long targetAccountId;
    private Double amount;
    private Long sourceCardNumber;
    private Long targetCardNumber;

}
