package com.abderrahmane.elearning.socialservice.converters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.socialservice.models.City;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class JsonCityConverter implements Converter<City, Map<String, Object>> {
    @Override
    public Map<String, Object> convert(City source) {
        Map<String, Object> cityMap = new HashMap<>();
        List<String> names = new ArrayList<>(List.of(source.getName()));

        if (source.getState().getName().length() > 0) names.add(source.getState().getName());

        names.add(source.getState().getCountry().getName());

        cityMap.put("id", source.getId());
        cityMap.put("name", source.getName());
        cityMap.put("fullname", String.join(", ", names));
    
        return cityMap;
    }

    public List<Map<String, Object>> convertList (List<City> cities) {
        return cities.stream().map(city -> this.convert(city)).toList();
    }
}
