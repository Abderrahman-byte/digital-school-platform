package com.abderrahmane.elearning.socialservice.converters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.common.models.SchoolProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class JsonSchoolProfileConverter implements Converter<SchoolProfile, Map<String, Object>> {
    @Autowired
    private StringCityConverter stringCityConverter;

    @Override
    public Map<String, Object> convert(SchoolProfile source) {
        Map<String, Object> data = new HashMap<>();

        data.put("name", source.getName());
        data.put("id", source.getId());
        data.put("subtitle", source.getSubtitle());
        data.put("overview", source.getOverview());
        data.put("location", stringCityConverter.convert(source.getLocation()));

        return data;
    }

    public List<Map<String, Object>> convertList (List<SchoolProfile> profiles) {
        return profiles.stream().map(profile -> this.convert(profile)).toList();
    }
}
