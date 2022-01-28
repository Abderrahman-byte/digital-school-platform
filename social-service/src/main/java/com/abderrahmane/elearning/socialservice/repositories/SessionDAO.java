package com.abderrahmane.elearning.socialservice.repositories;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.abderrahmane.elearning.socialservice.annotations.ClearCache;
import com.abderrahmane.elearning.socialservice.models.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionDAO {
    @Autowired
    private EntityManager entityManager;    

    @ClearCache
    public Session selectById (String id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Session> cq = cb.createQuery(Session.class);
        Root<Session> root = cq.from(Session.class);

        cq.select(root).where(
            cb.and(
                cb.greaterThan(root.get("expires"), Calendar.getInstance()), 
                cb.equal(root.get("sid"), id)
            )
        );

        try {
            return (Session)entityManager.createQuery(cq).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
