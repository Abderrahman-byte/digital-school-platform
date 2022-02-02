package com.abderrahmane.elearning.common.repositories;

import java.util.Calendar;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.abderrahmane.elearning.common.annotations.ClearCache;
import com.abderrahmane.elearning.common.annotations.HandleTransactions;
import com.abderrahmane.elearning.common.models.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SessionDAO {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CriteriaBuilder criteriaBuilder;

    @HandleTransactions
    public Session save (Map<String, Object> payload) {
        Session session = new Session();
        session.setPayload(payload);
        session.getExpires().add(Calendar.MONTH, 1);

        entityManager.persist(session);

        return session;
    }

    @ClearCache
    public Session select (String id) {
        CriteriaQuery<Session> cq = criteriaBuilder.createQuery(Session.class);
        Root<Session> root = cq.from(Session.class);

        cq.select(root).where(
            criteriaBuilder.and(
                criteriaBuilder.equal(root.get("sid"), id),
                criteriaBuilder.greaterThan(root.<Calendar>get("expires"), Calendar.getInstance())
            )
        );

        try {
            return (Session)entityManager.createQuery(cq).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
