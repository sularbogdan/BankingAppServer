package com.example.banking.service.implementation;


import com.example.banking.exception.AccountNotFoundException;
import com.example.banking.exception.InsufficientFundsException;
import com.example.banking.exception.ResourceNotFoundException;
import com.example.banking.repository.AccountRepository;
import com.example.banking.repository.dto.AccountDTO;
import com.example.banking.repository.dto.LoginDTO;
import com.example.banking.repository.dto.RegisterDTO;
import com.example.banking.repository.dto.TransferRequestDTO;
import com.example.banking.repository.entity.Account;
import com.example.banking.service.AccountService;
import com.example.banking.util.AccountMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public AccountServiceImpl(AccountRepository accountRepository) {

        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDTO getAccountbyID(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(()
                -> new AccountNotFoundException("Accound not found!"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDTO deposit(Long id, double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found!"));
        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        Account updatedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(updatedAccount);
    }

    @Override
    public AccountDTO withdraw(Long id, double amount) {
        Account account = accountRepository.findById(id).
                orElseThrow(() -> new AccountNotFoundException("Accound not found!"));
        if (account.getBalance() < amount) {
            throw new RuntimeException("Sold insuficient!");
        }
        double newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);
        Account updatedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(updatedAccount);
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(AccountMapper::mapToAccountDto).collect(Collectors.toList());

    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found!"));
        accountRepository.deleteById(id);
    }

    @Override
    public double sold(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(()
                -> new AccountNotFoundException("Accound not found!"));
        return account.getBalance();
    }

    @Override
    public void transfer(TransferRequestDTO transferRequest) {
        Double amount = transferRequest.getAmount();
        Long sourceCardNumber = transferRequest.getSourceCardNumber();
        Long targetCardNumber = transferRequest.getTargetCardNumber();

        Account sourceAccount = accountRepository.findByCardNumber(sourceCardNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Source account not found!"));
        Account targetAccount = accountRepository.findByCardNumber(targetCardNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Target account not found!"));
        if (sourceAccount.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient funds!");
        }
        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        targetAccount.setBalance(targetAccount.getBalance() + amount);
        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);
    }

    @Override
    public Account register(RegisterDTO registerDTO) {
        accountRepository.findByCardNumber(registerDTO.getCardNumber())
                .ifPresent(account -> {
                    throw new IllegalStateException("Card number already used");
                });
        Account account = new Account();
        account.setName(registerDTO.getName());
        account.setCardNumber(registerDTO.getCardNumber());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(registerDTO.getPassword());
        account.setPassword(hashedPassword);
        return accountRepository.save(account);
    }

    @Override
    public Account login(LoginDTO loginDTO) {
        Optional<Account> accountOptional = accountRepository.
                findByNameAndCardNumber(loginDTO.getName(), loginDTO.getCardNumber());

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(loginDTO.getPassword(), account.getPassword())) {
                return account;
            }
        }
        return null;
    }

}

