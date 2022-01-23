package com.abderrahmane.elearning.authservice.repositories;

import java.util.Calendar;
import java.util.Map;

import javax.persistence.EntityManager;

import com.abderrahmane.elearning.authservice.annotations.HandleTransactions;
import com.abderrahmane.elearning.authservice.models.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SessionRepository {
    @Autowired
    private EntityManager entityManager;

    @HandleTransactions
    public Session save (Map<String, Object> payload) {
        Session session = new Session();
        session.setPayload(payload);
        session.getExpires().add(Calendar.MONTH, 1);

        entityManager.persist(session);

        return session;
    }
}
