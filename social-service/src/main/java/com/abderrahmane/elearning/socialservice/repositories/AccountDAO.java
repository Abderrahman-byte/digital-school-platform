package com.abderrahmane.elearning.socialservice.repositories;

import javax.persistence.EntityManager;

import com.abderrahmane.elearning.socialservice.models.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// TODO : management transaction in a aop style

@Repository
public class AccountDAO {
    @Autowired
    private EntityManager entityManager;

    public Account selectById (String id) {
        return entityManager.find(Account.class, id);
    }

    public Account saveAccount (Account account) {
        entityManager.getTransaction().begin();

        entityManager.persist(account);

        entityManager.getTransaction().commit();

        return account;
    }
}
