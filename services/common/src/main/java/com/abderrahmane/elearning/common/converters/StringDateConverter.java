package com.abderrahmane.elearning.common.converters;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringDateConverter implements Converter<Date, String> {
    private SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");

    @Override
    public String convert(Date source) {
        return formatter.format(source);
    }

    public String convert (Calendar source) {
        return this.convert(source.getTime());
    }
}
