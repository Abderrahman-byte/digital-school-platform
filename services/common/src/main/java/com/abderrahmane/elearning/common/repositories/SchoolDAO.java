package com.abderrahmane.elearning.common.repositories;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.abderrahmane.elearning.common.annotations.HandleTransactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SchoolDAO {
    @Autowired
    private EntityManager entityManager;

    @HandleTransactions
    public boolean acceptTeacher (String schoolId, String teacherId) {
        Query query =  entityManager.createNativeQuery("UPDATE teacher_school SET verified = true WHERE teacher_id = ? AND school_id = ? AND verified = false");
        query.setParameter(1, teacherId);
        query.setParameter(2, schoolId);

        return query.executeUpdate() > 0;
    }
}
