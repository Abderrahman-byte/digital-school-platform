package com.abderrahmane.elearning.common.converters;

import java.util.ArrayList;
import java.util.List;

import com.abderrahmane.elearning.common.models.City;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringCityConverter implements Converter<City, String> {
    @Override
    public String convert(City source) {
        List<String> parts = new ArrayList<>(List.of(source.getName()));

        if (source.getState().getName().length() > 0) parts.add(source.getState().getName());

        parts.add(source.getState().getCountry().getName());

        return String.join(", ", parts);
    }
}
