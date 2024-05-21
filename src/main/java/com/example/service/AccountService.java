package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for Account using the Spring framework
 */
@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired 
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Given an account, adds the account to the Account Database.
     * 
     * To successfully add the account, the account must:
     * 1. NOT be blank
     * 2. password length GREATER THAN 3
     * 3. unique username not already in account database
     * 
     * @param account
     * @return the account if successfully added, null if otherwise
     */
    public Account addAccount(Account account) {
        System.out.println("*****INSIDE MY SERVICE METHOD*******");
        Account newAccount = account;

        return accountRepository.save(newAccount);
    }
}
