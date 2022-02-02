package com.abderrahmane.elearning.authservice.converters;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.abderrahmane.elearning.common.models.StudentProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StudentProfileMapConverter implements Converter<StudentProfile, Map<String, Object>> {
    @Autowired
    private LocationStringConverter locationStringConverter;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");

    @Override
    public Map<String, Object> convert(StudentProfile source) {
        Map<String, Object> data = new HashMap<>();

        data.put("firstname", source.getFirstname());
        data.put("lastname", source.getLastname());
        data.put("dayOfBirth", dateFormat.format(source.getDayOfBirth().getTime()));

        if (source.getLocation() != null) {
            data.put("location", locationStringConverter.convert(source.getLocation()));
        }

        return data;
    }
}
