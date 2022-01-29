package com.abderrahmane.elearning.authservice.converters;

import java.util.HashMap;
import java.util.Map;

import com.abderrahmane.elearning.authservice.models.SchoolProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SchoolProfileMapConverter implements Converter<SchoolProfile, Map<String, Object>> {
    @Autowired
    private LocationStringConverter locationConverter;

    @Override
    public Map<String, Object> convert(SchoolProfile source) {
        Map<String, Object> data = new HashMap<>();

        data.put("name", source.getName());
        data.put("subtitle", source.getSubtitle());
        data.put("overview", source.getOverview());
        data.put("location", locationConverter.convert(source.getLocation()));

        return data;
    }
    
}
