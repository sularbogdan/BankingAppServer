package com.example.banking.controller;


import com.example.banking.exception.AccountNotFoundException;
import com.example.banking.exception.InsufficientFundsException;
import com.example.banking.repository.dto.*;
import com.example.banking.repository.entity.Account;
import com.example.banking.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountId(@PathVariable Long id) {
        AccountDTO accountDto = accountService.getAccountbyID(id);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDTO> deposit(@PathVariable Long id, @RequestBody DepositRequestDTO depositRequest) {
        try {
            AccountDTO accountDTO = accountService.deposit(id, depositRequest.getAmount());
            return ResponseEntity.ok(accountDTO);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable Long id, @RequestBody WithdrawRequestDTO withdrawRequest) {
        try {

            AccountDTO accountDTO = accountService.withdraw(id, withdrawRequest.getAmount());
            return ResponseEntity.ok(accountDTO);
        } catch (InsufficientFundsException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Insufficient funds!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "An error occurred during withdrawal.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        try {
            accountService.deleteAccount(id);
            return ResponseEntity.ok().body("Account deleted successfully");
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting account");
        }
    }


    @PutMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequestDTO transferRequest) {
        try {
            accountService.transfer(transferRequest);
            return ResponseEntity.ok("Transfer successful!");
        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occured!");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody RegisterDTO register) {
        Account newAccount = accountService.register(register);
        return ResponseEntity.ok(newAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            Account account = accountService.login(loginDTO);
            if (account != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Login successful");
                response.put("name", account.getName());
                response.put("accountId", account.getId());
                response.put("balance", account.getBalance());

                return ResponseEntity.ok(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Invalid credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "An error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}



