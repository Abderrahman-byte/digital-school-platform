package com.abderrahmane.elearning.socialservice.converters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.common.models.City;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class JsonCityConverter implements Converter<City, Map<String, Object>> {
    @Autowired
    private StringCityConverter stringCityConverter;

    @Override
    public Map<String, Object> convert(City source) {
        Map<String, Object> cityMap = new HashMap<>();

        cityMap.put("id", source.getId());
        cityMap.put("name", source.getName());
        cityMap.put("fullname", stringCityConverter.convert(source));
    
        return cityMap;
    }

    public List<Map<String, Object>> convertList (List<City> cities) {
        return cities.stream().map(city -> this.convert(city)).toList();
    }
}
