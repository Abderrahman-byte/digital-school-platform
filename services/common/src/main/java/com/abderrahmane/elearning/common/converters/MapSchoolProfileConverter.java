package com.abderrahmane.elearning.common.converters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.common.models.SchoolProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MapSchoolProfileConverter implements Converter<SchoolProfile, Map<String, Object>> {
    @Autowired
    private StringCityConverter locationConverter;

    @Override
    public Map<String, Object> convert(SchoolProfile source) {
        Map<String, Object> data = new HashMap<>();

        data.put("id", source.getId());
        data.put("name", source.getName());
        data.put("subtitle", source.getSubtitle());
        data.put("overview", source.getOverview());
        data.put("location", locationConverter.convert(source.getLocation()));

        return data;
    }   
    
    public List<Map<String, Object>> convertList (List<SchoolProfile> sources) {
        return sources.stream().map(source -> this.convert(source)).toList();
    }
}
