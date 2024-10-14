package com.example.banking.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Table(name = "accounts")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_holder_name")
    private String name;

    @Column(name = "card_number", length = 8, unique = true)
    private Long cardNumber;

    @Column(name = "balance")
    private double balance;

    @Column(name = "password")
    private String password;


    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", cardNumber='" + cardNumber + '\''
                + ", balance='" + balance + '\''
                + ", password='" + password + '\''
                + '}';
    }
}


