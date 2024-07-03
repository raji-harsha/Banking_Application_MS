package com.scb.account.repository;

import com.scb.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.customerId = :customerId")
    Account findAccountByCustomerId(@Param("customerId") Long customerId);

    Optional<Account> findByAccountNumber(String fromAccountId);
}
