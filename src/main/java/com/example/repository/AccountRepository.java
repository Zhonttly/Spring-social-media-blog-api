package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Account;
import org.hibernate.annotations.Generated;

/**
 * Account repository using Spring framework that handles queries
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Finds an account within the database through a username
     * 
     * @param username username of the account to search for
     * @return The account if found, null if empty
     */
    Account findAccountByUsername(String username);

    /**
     * Finds an account within the database through its account id
     * 
     * @param accountId account ID of the account to search for
     * @return The account if found, null if empty
     */
    Account findAccountByAccountId(Integer accountId);
}
