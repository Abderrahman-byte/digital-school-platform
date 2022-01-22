package com.abderrahmane.elearning.authservice.repositories;

import javax.persistence.EntityManager;

import com.abderrahmane.elearning.authservice.annotations.HandleTransactions;
import com.abderrahmane.elearning.authservice.models.Account;
import com.abderrahmane.elearning.authservice.models.AccountType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository {
    @Autowired
    private EntityManager entityManager;

    @HandleTransactions
    public Account insertAccount(String username, String email, String password) {
        return this.insertAccount(username, email, password, AccountType.STUDENT);
    }

    @HandleTransactions
    public Account insertAccount(String username, String email, String password, String accountType) {
        if (accountType == null) accountType = "student";
        return this.insertAccount(username, email, password, AccountType.valueOf(accountType.toUpperCase()));
    }

    @HandleTransactions
    public Account insertAccount(String username, String email, String password, AccountType accountType) {
        Account account = new Account();
        account.setPassword(password);
        account.setUsername(username);
        account.setEmail(email);
        account.setAccountType(accountType);

        entityManager.persist(account);
        entityManager.flush();

        return account;
    }

}
