package com.abderrahmane.elearning.common.repositories;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import com.abderrahmane.elearning.common.annotations.ClearCache;
import com.abderrahmane.elearning.common.annotations.HandleTransactions;
import com.abderrahmane.elearning.common.helpers.PasswordEncoder;
import com.abderrahmane.elearning.common.models.Account;
import com.abderrahmane.elearning.common.models.AccountType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDAO {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CriteriaBuilder criteriaBuilder;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        account.setPassword(passwordEncoder.encode(password));
        account.setUsername(username);
        account.setEmail(email);
        account.setAccountType(accountType);

        entityManager.persist(account);
        entityManager.flush();

        return account;
    }

    @HandleTransactions
    public boolean activateAccount (String id) {
        CriteriaUpdate<Account> cq = this.criteriaBuilder.createCriteriaUpdate(Account.class);
        Root<Account> root = cq.from(Account.class);

        cq.set(root.get("isActive"), true).where(criteriaBuilder.equal(root.get("id"), id));
        return this.entityManager.createQuery(cq).executeUpdate() > 0;
    }

    @ClearCache
    public Account select (String id) {
        return entityManager.find(Account.class, id);
    }

    @ClearCache
    public Account selectByUsername (String username) {
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> root = criteriaQuery.from(Account.class);

        criteriaQuery.select(root).where(
            criteriaBuilder.or(
                criteriaBuilder.equal(root.get("username"), username), 
                criteriaBuilder.equal(root.get("email"), username)
            )
        );

        Query query = entityManager.createQuery(criteriaQuery);
        
        try {
            return (Account)query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    @HandleTransactions
    public Account saveAccount (Account account) {
        entityManager.persist(account);

        return account;
    }
}
