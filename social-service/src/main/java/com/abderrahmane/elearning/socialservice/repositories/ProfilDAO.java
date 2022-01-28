package com.abderrahmane.elearning.socialservice.repositories;

import javax.persistence.EntityManager;

import com.abderrahmane.elearning.socialservice.annotations.WrapTransaction;
import com.abderrahmane.elearning.socialservice.models.Account;
import com.abderrahmane.elearning.socialservice.models.City;
import com.abderrahmane.elearning.socialservice.models.SchoolProfil;
import com.abderrahmane.elearning.socialservice.models.TeacherProfil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProfilDAO {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AccountDAO accountDAO;

    public SchoolProfil createSchoolProfil (String name, String overview, String subtitle, Account account, City location) {
        SchoolProfil schoolProfil = new SchoolProfil();

        schoolProfil.setAccount(account);
        schoolProfil.setName(name);
        schoolProfil.setSubtitle(subtitle);
        schoolProfil.setOverview(overview);
        schoolProfil.setLocation(location);
        account.setSchoolProfil(schoolProfil);

        accountDAO.saveAccount(account);

        return schoolProfil;
    }

    public TeacherProfil createTeacherProfil (String firstname, String lastname, String title, String bio, Account account, City location) {
        TeacherProfil teacherProfil = new TeacherProfil();

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

    @WrapTransaction
    public SchoolProfil saveSchoolProfil (SchoolProfil schoolProfil) {
        entityManager.persist(schoolProfil);

        return schoolProfil;
    }
}
