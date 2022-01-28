package com.abderrahmane.elearning.socialservice.repositories;

import javax.persistence.EntityManager;

import com.abderrahmane.elearning.socialservice.annotations.ClearCache;
import com.abderrahmane.elearning.socialservice.annotations.WrapTransaction;
import com.abderrahmane.elearning.socialservice.models.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// TODO : Translate transactions

@Repository
public class AccountDAO {
    @Autowired
    private EntityManager entityManager;

    @ClearCache
    public Account selectById (String id) {
        return entityManager.find(Account.class, id);
    }

    @WrapTransaction
    public Account saveAccount (Account account) {
        entityManager.persist(account);

        return account;
    }
}
