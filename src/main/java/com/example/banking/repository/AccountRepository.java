package com.example.banking.repository;

import com.example.banking.repository.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.cardNumber =:cardNumber")
    Optional<Account> findByCardNumber(@Param("cardNumber") Long cardNumber);

    @Query("SELECT a FROM Account a WHERE a.name = :name AND a.cardNumber = :cardNumber")
    Optional<Account> findByNameAndCardNumber(@Param("name") String name, @Param("cardNumber") Long cardNumber);

}
