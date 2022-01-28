package com.abderrahmane.elearning.socialservice.repositories;

import javax.persistence.EntityManager;

import com.abderrahmane.elearning.socialservice.annotations.WrapTransaction;
import com.abderrahmane.elearning.socialservice.models.Account;
import com.abderrahmane.elearning.socialservice.models.City;
import com.abderrahmane.elearning.socialservice.models.SchoolProfil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProfilDAO {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AccountDAO accountDAO;

    @WrapTransaction
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

    @WrapTransaction
    public SchoolProfil saveSchoolProfil (SchoolProfil schoolProfil) {
        entityManager.persist(schoolProfil);

        return schoolProfil;
    }
}
