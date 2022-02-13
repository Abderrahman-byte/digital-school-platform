package com.abderrahmane.elearning.common.repositories;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.abderrahmane.elearning.common.annotations.ClearCache;
import com.abderrahmane.elearning.common.annotations.HandleTransactions;
import com.abderrahmane.elearning.common.models.Account;
import com.abderrahmane.elearning.common.models.City;
import com.abderrahmane.elearning.common.models.SchoolProfile;
import com.abderrahmane.elearning.common.models.StudentProfile;
import com.abderrahmane.elearning.common.models.TeacherProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileDAO {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AccountDAO accountDAO;

    public SchoolProfile createSchoolProfil (String name, String overview, String subtitle, Account account, City location) {
        SchoolProfile schoolProfil = new SchoolProfile();

        schoolProfil.setAccount(account);
        schoolProfil.setName(name);
        schoolProfil.setSubtitle(subtitle);
        schoolProfil.setOverview(overview);
        schoolProfil.setLocation(location);
        account.setSchoolProfile(schoolProfil);

        accountDAO.saveAccount(account);

        return schoolProfil;
    }

    public TeacherProfile createTeacherProfil (String firstname, String lastname, String title, String bio, Account account, City location) {
        TeacherProfile teacherProfil = new TeacherProfile();

        teacherProfil.setAccount(account);
        teacherProfil.setFirstname(firstname);
        teacherProfil.setLastname(lastname);
        teacherProfil.setBio(bio);
        teacherProfil.setTitle(title);
        teacherProfil.setLocation(location);
        account.setTeacherProfile(teacherProfil);

        accountDAO.saveAccount(account);

        return teacherProfil;
    }

    public StudentProfile creatStudentProfile(String firstname, String lastname, Calendar dayOfBirth, City location, Account account) {
        StudentProfile studentProfile = new StudentProfile();

        studentProfile.setAccount(account);
        studentProfile.setDayOfBirth(dayOfBirth);
        studentProfile.setFirstname(firstname);
        studentProfile.setLastname(lastname);
        studentProfile.setLocation(location);
        account.setStudentProfile(studentProfile);

        accountDAO.saveAccount(account);

        return studentProfile;
    }

    @HandleTransactions
    public SchoolProfile saveSchoolProfil (SchoolProfile schoolProfil) {
        entityManager.persist(schoolProfil);

        return schoolProfil;
    }

    // TODO : Implement advance search for best result
    // TODO : Escape query

    @ClearCache
    @SuppressWarnings("unchecked")
    public List<SchoolProfile> searchSchool (String name, int limit) {
        Query query = entityManager.createNativeQuery("select * from school_profil where name ~* ? LIMIT ?", SchoolProfile.class);
        query.setParameter(1, ".*" + name + ".*");
        query.setParameter(2, limit);

        return (List<SchoolProfile>)query.getResultList();
    }

    @HandleTransactions
    public boolean teacherJoinSchool(String teacherId, String schoolId, String title) {
        Query query = entityManager.createNativeQuery("INSERT INTO teacher_school (teacher_id, school_id, title) VALUES (?, ?, ?)");
        query.setParameter(1, teacherId);
        query.setParameter(2, schoolId);
        query.setParameter(3, title);

        return query.executeUpdate() > 0;
    }

    @HandleTransactions
    public boolean deleteTeacherSchool (String teacherId, String schoolId) {
        Query query = entityManager.createNativeQuery("DELETE FROM teacher_school WHERE teacher_id = ? AND school_id = ?");
        query.setParameter(1, teacherId);
        query.setParameter(2, schoolId);

        return query.executeUpdate() > 0;
    }

    @HandleTransactions
    public boolean endTeacherSchool (String teacherId, String schoolId) {
        Query query = entityManager.createNativeQuery("UPDATE teacher_school SET ended_date = NOW() WHERE teacher_id = ? AND school_id = ? AND verified = true");
        query.setParameter(1, teacherId);
        query.setParameter(2, schoolId);

        return query.executeUpdate() > 0;
    }

    @HandleTransactions
    public boolean teacherRejoinSchool (String teacherId, String schoolId, String title) {
        Query query = entityManager.createNativeQuery("UPDATE teacher_school SET ended_date = NULL, verified = false, title = ? WHERE teacher_id = ? AND school_id = ?");
        query.setParameter(1, title);
        query.setParameter(2, teacherId);
        query.setParameter(3, schoolId);

        return query.executeUpdate() > 0;
    }

    @HandleTransactions
    public boolean updateTeacherProfile (Map<String, Object> data, String teacherId) {
        String sqlString = "UPDATE teacher_profil SET "
                + String.join(", ", data.keySet().stream().map(key -> key + " = ?").toList()) 
                + " WHERE account_id = ?";
        Query query = this.entityManager.createNativeQuery(sqlString);
        Object[] values = data.values().toArray();
        
        System.out.println("QUERY => "+sqlString);

        for (int i = 0; i < values.length; i++) query.setParameter(i + 1, values[i]);

        query.setParameter(values.length + 1, teacherId);

        return query.executeUpdate() > 0;
    }
}
