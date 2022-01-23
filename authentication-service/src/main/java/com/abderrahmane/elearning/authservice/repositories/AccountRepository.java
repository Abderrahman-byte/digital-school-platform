package com.abderrahmane.elearning.authservice.repositories;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import com.abderrahmane.elearning.authservice.annotations.HandleTransactions;
import com.abderrahmane.elearning.authservice.models.Account;
import com.abderrahmane.elearning.authservice.models.AccountType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CriteriaBuilder criteriaBuilder;

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

    @HandleTransactions
    public boolean activateAccount (String id) {
        CriteriaUpdate<Account> cq = this.criteriaBuilder.createCriteriaUpdate(Account.class);
        Root<Account> root = cq.from(Account.class);

        cq.set(root.get("isActive"), true).where(criteriaBuilder.equal(root.get("id"), id));
        return this.entityManager.createQuery(cq).executeUpdate() > 0;
    }

    public Account select (String id) {
        return entityManager.find(Account.class, id);
    } 
}
