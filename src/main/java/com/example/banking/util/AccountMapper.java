package com.example.banking.util;

import com.example.banking.repository.dto.AccountDTO;
import com.example.banking.repository.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public static Account mapToAccount(AccountDTO accountDto) {
        return new Account(accountDto.getId(),
                accountDto.getName(),
                accountDto.getCardNumber(),
                accountDto.getBalance(),
                accountDto.getPassword());

    }

    public static AccountDTO mapToAccountDto(Account account) {
        AccountDTO accountDto = new AccountDTO();
        accountDto.setId(account.getId());
        accountDto.setName(account.getName());
        accountDto.setCardNumber(account.getCardNumber());
        accountDto.setBalance(account.getBalance());
        accountDto.setPassword(account.getPassword());
        return accountDto;
    }
}
