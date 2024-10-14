package com.example.banking.service;


import com.example.banking.repository.dto.AccountDTO;
import com.example.banking.repository.dto.LoginDTO;
import com.example.banking.repository.dto.RegisterDTO;
import com.example.banking.repository.dto.TransferRequestDTO;
import com.example.banking.repository.entity.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {

    AccountDTO getAccountbyID(Long id);

    AccountDTO deposit(Long id, double amount);

    AccountDTO withdraw(Long id, double amount);

    List<AccountDTO> getAllAccounts();

    void deleteAccount(Long id);

    double sold(Long id);


    void transfer(TransferRequestDTO transferRequest);


    Account register(RegisterDTO register);


    Account login(LoginDTO loginDTO);
}