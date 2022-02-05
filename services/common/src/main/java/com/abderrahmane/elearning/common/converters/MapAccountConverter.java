package com.abderrahmane.elearning.common.converters;

import java.util.HashMap;
import java.util.Map;

import com.abderrahmane.elearning.common.models.Account;
import com.abderrahmane.elearning.common.models.AccountType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MapAccountConverter implements Converter<Account, Map<String, Object>> {
    @Autowired
    private MapSchoolProfileConverter schoolProfileMapConverter;

    @Autowired
    private MapTeacherProfileConverter teacherProfileMapConverter;

    @Autowired
    private MapStudentProfileConverter studentProfileMapConverter;

    @Override
    public Map<String, Object> convert(Account source) {
        Map<String, Object> data = new HashMap<>();

        data.put("id", source.getId());
        data.put("username", source.getUsername());
        data.put("email", source.getEmail());
        data.put("accountType", source.getAccountType());
        data.put("isAdmin", source.isAdmin());

        this.putProfileData(source, data);

        return data;
    }

    private void putProfileData (Account source, Map<String, Object> data) {
       Map<String, Object> profile = null;

        if (source.getAccountType().equals(AccountType.SCHOOL) && source.getSchoolProfile() != null) {
            profile = schoolProfileMapConverter.convert(source.getSchoolProfile());
        } else if (source.getAccountType().equals(AccountType.TEACHER) && source.getTeacherProfile() != null) {
            profile = teacherProfileMapConverter.convert(source.getTeacherProfile());
        } else if (source.getStudentProfile() != null) {
            profile = studentProfileMapConverter.convert(source.getStudentProfile());
        }

        if (profile != null && profile.containsKey("id")) profile.remove("id");

        data.put("profile", profile);
    }
}
