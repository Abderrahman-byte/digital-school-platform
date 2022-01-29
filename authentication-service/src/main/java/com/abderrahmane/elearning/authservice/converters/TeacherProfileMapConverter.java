package com.abderrahmane.elearning.authservice.converters;

import java.util.HashMap;
import java.util.Map;

import com.abderrahmane.elearning.authservice.models.TeacherProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TeacherProfileMapConverter implements Converter<TeacherProfile, Map<String, Object>> {
    @Autowired
    private LocationStringConverter locationStringConverter;

    @Override
    public Map<String, Object> convert(TeacherProfile source) {
        Map<String, Object> data = new HashMap<>();

        data.put("firstname", source.getFirstname());
        data.put("lastname", source.getLastname());
        data.put("bio", source.getBio());
        data.put("title", source.getTitle());
        
        if (source.getLocation() != null) {
            data.put("location", locationStringConverter.convert(source.getLocation()));
        }

        return data;
    }
}
