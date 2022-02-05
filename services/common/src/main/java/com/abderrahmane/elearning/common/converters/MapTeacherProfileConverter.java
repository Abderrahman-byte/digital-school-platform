package com.abderrahmane.elearning.common.converters;

import java.util.HashMap;
import java.util.Map;

import com.abderrahmane.elearning.common.models.TeacherProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MapTeacherProfileConverter implements Converter<TeacherProfile, Map<String, Object>> {
    @Autowired
    private StringCityConverter locationStringConverter;

    @Override
    public Map<String, Object> convert(TeacherProfile source) {
        Map<String, Object> data = new HashMap<>();

        data.put("id", source.getId());
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
