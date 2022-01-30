package com.abderrahmane.elearning.socialservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.socialservice.converters.JsonCityConverter;
import com.abderrahmane.elearning.socialservice.models.City;
import com.abderrahmane.elearning.socialservice.repositories.GeoDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/search/city")
public class SearchCityController {
    @Autowired
    private JsonCityConverter cityConverter;

    @Autowired
    private GeoDAO geoDAO;

    @GetMapping(params = "query")
    public Map<String, Object> searchCity (@RequestParam(name = "query") String query) {
        Map<String, Object> response = new HashMap<>();
        List<City> results = geoDAO.searchCity(query, 20);

        response.put("ok", true);
        response.put("data", cityConverter.convertList(results));

        return response;
    }
}
