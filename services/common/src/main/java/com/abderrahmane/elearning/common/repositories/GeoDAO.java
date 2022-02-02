package com.abderrahmane.elearning.common.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.abderrahmane.elearning.common.models.City;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeoDAO {
    @Autowired
    private EntityManager entityManager;

    public City getCity(int id) {
        return entityManager.find(City.class, id);
    }

    // TODO : escape search query
    // TODO : Implement Advance search for best result
    @SuppressWarnings("unchecked")
    public List<City> searchCity(String name, int limit) {
        Query query = entityManager.createNativeQuery("SELECT * FROM city where name % ? LIMIT ?", City.class);
        query.setParameter(1, "^" + name);
        query.setParameter(2, limit);

        return (List<City>)query.getResultList();
    }
}
