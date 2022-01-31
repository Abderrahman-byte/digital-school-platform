package com.abderrahmane.elearning.socialservice.converters;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringDateConverter implements Converter<Calendar, String> {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");

    @Override
    public String convert(Calendar source) {
        return dateFormat.format(source.getTime());
    }

    public String convert(Date source) {
        return dateFormat.format(source);
    }
}
