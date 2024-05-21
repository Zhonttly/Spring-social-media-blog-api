package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Account;

/**
 * Account repository using Spring framework that handles queries
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

}
