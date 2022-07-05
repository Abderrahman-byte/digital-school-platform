package com.abderrahmane.elearning.common.converters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.common.models.Group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MapGroupConverter implements Converter<Group, Map<String, Object>> {
    @Autowired
    private StringDateTimeConverter dateTimeConverter;

    @Autowired
    private MapTeacherProfileConverter teacherProfileConverter;

    @Override
    public Map<String, Object> convert(Group source) {
        return this.convert(source, false);
    }

    public Map<String, Object> convert(Group source, boolean putCreator) {
        Map<String, Object> data = new HashMap<>();

        data.put("id", source.getId());
        data.put("createdDate", dateTimeConverter.convert(source.getCreatedDate()));
        data.put("dsecription", source.getDescription());
        data.put("label", source.getLabel());

        if (putCreator) data.put("arg0", teacherProfileConverter.convert(source.getCreatedBy()));
        
        return null;
    }

    public List<Map<String, Object>> convertList (List<Group> groups) {
        return groups.stream().map(group -> this.convert(group)).toList();
    }
}
