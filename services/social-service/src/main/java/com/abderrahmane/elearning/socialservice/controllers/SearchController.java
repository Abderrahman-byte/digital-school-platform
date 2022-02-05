package com.abderrahmane.elearning.socialservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.common.converters.MapCityConverter;
import com.abderrahmane.elearning.common.converters.MapSchoolProfileConverter;
import com.abderrahmane.elearning.common.models.City;
import com.abderrahmane.elearning.common.models.SchoolProfile;
import com.abderrahmane.elearning.common.repositories.GeoDAO;
import com.abderrahmane.elearning.common.repositories.ProfileDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/search")
public class SearchController {
    @Autowired
    private MapCityConverter cityConverter;

    @Autowired
    private MapSchoolProfileConverter schoolProfileConverter;

    @Autowired
    private GeoDAO geoDAO;

    @Autowired
    private ProfileDAO profileDAO;

    @GetMapping(path="/city", params = "query")
    public Map<String, Object> searchCity (@RequestParam(name = "query") String query) {
        Map<String, Object> response = new HashMap<>();
        List<City> results = geoDAO.searchCity(query, 20);

        response.put("ok", true);
        response.put("data", cityConverter.convertList(results));

        return response;
    }

    @GetMapping(path="/school", params = "query")
    public Map<String, Object> searchSchool (@RequestParam(name = "query") String name) {
        Map<String, Object> response = new HashMap<>();
        List<SchoolProfile> results = profileDAO.searchSchool(name, 20);

        response.put("ok", true);
        response.put("data", schoolProfileConverter.convertList(results));

        return response;
    }
}
