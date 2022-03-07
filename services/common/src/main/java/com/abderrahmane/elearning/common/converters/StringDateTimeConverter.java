package com.abderrahmane.elearning.common.converters;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringDateTimeConverter implements Converter<Calendar, String> {
    private SimpleDateFormat dateFormater = new SimpleDateFormat("YYYY-MM-dd, HH:mm");

    @Override
    public String convert(Calendar source) {
        return dateFormater.format(source.getTime());
    }
    
}
