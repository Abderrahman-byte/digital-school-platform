package com.abderrahmane.elearning.common.converters;

import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.common.models.SchoolTeacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MapTeacherSchool implements Converter<SchoolTeacher, Map<String, Object>> {
    @Autowired
    private MapTeacherProfileConverter teacherProfileConverter;

    @Autowired
    private StringDateConverter dateConverter;

    @Override
    public Map<String, Object> convert(SchoolTeacher source) {
        Map<String, Object> data = teacherProfileConverter.convert(source.getTeacher());
        data.put("title", source.getTitle());

        data.put("createdDate", dateConverter.convert(source.getCreatedDate()));

        if (source.getEndedDate() != null) {
            data.put("endedDate", dateConverter.convert(source.getEndedDate()));
        }

        return data;
    }

    public List<Map<String, Object>> convertList (List<SchoolTeacher> source) {
        return source.stream().map(schoolTeacher -> this.convert(schoolTeacher)).toList();
    }
}
