package com.abderrahmane.elearning.socialservice.repositories;

import javax.persistence.EntityManager;

import com.abderrahmane.elearning.socialservice.models.Account;
import com.abderrahmane.elearning.socialservice.models.City;
import com.abderrahmane.elearning.socialservice.models.SchoolProfil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// TODO : management transaction in a aop style

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

    public SchoolProfil saveSchoolProfil (SchoolProfil schoolProfil) {
        entityManager.getTransaction().begin();

        entityManager.persist(schoolProfil);

        entityManager.getTransaction().commit();

        return schoolProfil;
    }
}
