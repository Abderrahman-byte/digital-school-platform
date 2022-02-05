package com.abderrahmane.elearning.common.converters;

import java.util.HashMap;
import java.util.Map;

import com.abderrahmane.elearning.common.models.StudentProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MapStudentProfileConverter implements Converter<StudentProfile, Map<String, Object>> {
    @Autowired
    private StringCityConverter locationStringConverter;

    @Autowired
    private StringDateConverter stringDateConverter;

    @Override
    public Map<String, Object> convert(StudentProfile source) {
        Map<String, Object> data = new HashMap<>();

        data.put("id", source.getId());
        data.put("firstname", source.getFirstname());
        data.put("lastname", source.getLastname());
        data.put("dayOfBirth",stringDateConverter.convert(source.getDayOfBirth()));

        if (source.getLocation() != null) {
            data.put("location", locationStringConverter.convert(source.getLocation()));
        }

        return data;
    }
}
