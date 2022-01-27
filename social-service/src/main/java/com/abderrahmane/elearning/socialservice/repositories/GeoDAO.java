package com.abderrahmane.elearning.socialservice.repositories;

import javax.persistence.EntityManager;

import com.abderrahmane.elearning.socialservice.models.City;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeoDAO {
    @Autowired
    private EntityManager entityManager;

    public City getCity (int id) {
        return entityManager.find(City.class, id);
    }
}
