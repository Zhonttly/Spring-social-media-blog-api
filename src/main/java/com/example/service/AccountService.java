package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hibernate.annotations.Generated;

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
        if(account.getPassword().length() >= 4 && account.getUsername().length() > 0) {
            return accountRepository.save(account);
        }
        
        return null;
    }

    /**
     * Checks if the account username and password match an existing 
     * pair within the database
     * 
     * @param account Account to validate
     * @return the account if pair matches, null otherwise
     */
    public Account validateAccount(Account account) {

        Account validateAccount = accountRepository.findAccountByUsername(account.getUsername());

        //Check if username exists
        if(validateAccount != null) {
            //Check if given password matches password within database
            if(validateAccount.getPassword().equals(account.getPassword())) {
                return validateAccount;
            }
        }

        return null;
    }
}
