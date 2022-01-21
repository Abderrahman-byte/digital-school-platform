package com.abderrahmane.elearning.authservice.repositories;

import javax.persistence.EntityManager;

import com.abderrahmane.elearning.authservice.models.Account;
import com.abderrahmane.elearning.authservice.models.AccountType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// TODO : add transaction handling via aop

@Repository
public class AccountRepository {
    @Autowired
    private EntityManager entityManager;

    public Account insertAccount(String username, String email, String password) {
        return this.insertAccount(username, email, password, AccountType.STUDENT);
    }

    public Account insertAccount(String username, String email, String password, String accountType) {
        if (accountType == null)
            accountType = "student";
        return this.insertAccount(username, email, password, AccountType.valueOf(accountType.toUpperCase()));
    }

    public Account insertAccount(String username, String email, String password, AccountType accountType) {
        Account account = new Account();
        account.setPassword(password);
        account.setUsername(username);
        account.setEmail(email);
        account.setAccountType(accountType);

        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }

            entityManager.persist(account);
            entityManager.flush(); // Flush to translate Exception before commiting

            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            if (entityManager.getTransaction().getRollbackOnly()) {
                entityManager.getTransaction().rollback();
            }

            throw ex;
        }

        return account;
    }

}
