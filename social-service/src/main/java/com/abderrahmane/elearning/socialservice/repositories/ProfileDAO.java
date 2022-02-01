package com.abderrahmane.elearning.socialservice.repositories;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.abderrahmane.elearning.socialservice.annotations.ClearCache;
import com.abderrahmane.elearning.socialservice.annotations.WrapTransaction;
import com.abderrahmane.elearning.socialservice.models.Account;
import com.abderrahmane.elearning.socialservice.models.City;
import com.abderrahmane.elearning.socialservice.models.SchoolProfile;
import com.abderrahmane.elearning.socialservice.models.StudentProfile;
import com.abderrahmane.elearning.socialservice.models.TeacherProfile;

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
        account.setSchoolProfil(schoolProfil);

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
        account.setTeacherProfil(teacherProfil);

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

    @WrapTransaction
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

    @WrapTransaction
    public boolean teacherJoinSchool(String teacherId, String schoolId, String title) {
        Query query = entityManager.createNativeQuery("INSERT INTO teacher_school (teacher_id, school_id, title) VALUES (?, ?, ?)");
        query.setParameter(1, teacherId);
        query.setParameter(2, schoolId);
        query.setParameter(3, title);

        return query.executeUpdate() > 0;
    }
}
