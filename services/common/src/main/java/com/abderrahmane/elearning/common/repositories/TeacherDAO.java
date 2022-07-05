package com.abderrahmane.elearning.common.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.abderrahmane.elearning.common.models.SchoolTeacher;

import org.springframework.stereotype.Repository;

@Repository
public class TeacherDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<SchoolTeacher> getTeacherSchools (String teacherId) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<SchoolTeacher> cq = cb.createQuery(SchoolTeacher.class);
        Root<SchoolTeacher> root = cq.from(SchoolTeacher.class);

        cq.select(root).where(
            cb.equal(root.get("teacherId"), teacherId)
        ).orderBy(
            cb.desc(root.get("addedDate"))
        );

        Query query = this.entityManager.createQuery(cq);

        return (List<SchoolTeacher>)query.getResultList();
    }
}
