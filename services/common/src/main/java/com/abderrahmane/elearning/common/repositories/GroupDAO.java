package com.abderrahmane.elearning.common.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import com.abderrahmane.elearning.common.models.Group;
import com.abderrahmane.elearning.common.models.TeacherProfile;

import org.springframework.stereotype.Repository;

@Repository
public class GroupDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<Group> getTeacherGroups (String teacherId, int limit, int offset) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> cq = cb.createQuery(Group.class);
        Root<Group> root = cq.from(Group.class);
        
        cq.select(root).where(
            cb.and(
                cb.equal(root.get("createdBy"), teacherId), 
                cb.isNull(root.get("deletedDate"))
            )
        ).orderBy(
            cb.desc(root.get("createdDate"))
        );

        Query query = this.entityManager.createQuery(cq);

        query.setMaxResults(limit);
        query.setFirstResult(offset);

        return (List<Group>)query.getResultList();
    }

    public List<Group> getStudentGroups (String studentId, int limit, int offset) {
        return null;
    }

    @Transactional
    public Group insertGroup (TeacherProfile teacher, String label, String description) {
        Group group = new Group();

        group.setCreatedBy(teacher);
        group.setDescription(description);
        group.setLabel(label);

        this.entityManager.persist(group);

        return group;
    }

    @Transactional
    public boolean updateGroup (String groupId, String teacherId, String label, String description) {
        if (label == null && description == null) return false;

        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaUpdate<Group> cu = cb.createCriteriaUpdate(Group.class);
        Root<Group> root = cu.from(Group.class);

        if (label != null) cu.set(root.get("label"), label);
        if (description != null) cu.set(root.get("description"), description);

        cu.where(
            cb.and(
                cb.equal(root.get("id"), groupId), 
                cb.equal(root.get("createdBy").get("id"), teacherId)
            )
        );

        Query query = this.entityManager.createQuery(cu);

        return query.executeUpdate() > 0;
    }

    @Transactional
    public boolean deleteGroup (String groupId, String teacherId) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<Group> cd = cb.createCriteriaDelete(Group.class);
        Root<Group> root = cd.from(Group.class);

        cd.where(
            cb.and(
                cb.equal(root.get("id"), groupId),
                cb.equal(root.get("createdBy").get("id"), teacherId)
            )  
        );

        return this.entityManager.createQuery(cd).executeUpdate() > 0;
    }

    @Transactional
    public boolean addMemberToGroup (String groupId, String studentId) {
        return false;
    }

    @Transactional
    public boolean removeStudentFromGroup (String groupId, String studentId) {
        return false;
    }
}
