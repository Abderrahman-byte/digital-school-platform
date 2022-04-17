package com.abderrahmane.elearning.common.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
public class SchoolDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public boolean acceptTeacher (String schoolId, String teacherId) {
        Query query =  entityManager.createNativeQuery("UPDATE teacher_school SET verified = true WHERE teacher_id = ? AND school_id = ? AND verified = false");
        query.setParameter(1, teacherId);
        query.setParameter(2, schoolId);

        return query.executeUpdate() > 0;
    }
}
