package com.abderrahmane.elearning.authservice.converters;

import java.util.HashMap;
import java.util.Map;

import com.abderrahmane.elearning.common.models.Account;
import com.abderrahmane.elearning.common.models.AccountType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

// TODO : Add Profil data

@Component
public class AccountMapConverter implements Converter<Account, Map<String, Object>> {
    @Autowired
    private SchoolProfileMapConverter schoolProfileMapConverter;

    @Autowired
    private TeacherProfileMapConverter teacherProfileMapConverter;

    @Autowired
    private StudentProfileMapConverter studentProfileMapConverter;

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
        System.out.println("TEACHER profile " + source.getTeacherProfile());
        System.out.println("School profile " + source.getTeacherProfile());
        System.out.println("STUDENT profile " + source.getStudentProfile());

        if (source.getAccountType().equals(AccountType.SCHOOL) && source.getSchoolProfile() != null) {
            data.put("profile", schoolProfileMapConverter.convert(source.getSchoolProfile()));
        } else if (source.getAccountType().equals(AccountType.TEACHER) && source.getTeacherProfile() != null) {
            data.put("profile", teacherProfileMapConverter.convert(source.getTeacherProfile()));
        } else if (source.getStudentProfile() != null) {
            data.put("profile", studentProfileMapConverter.convert(source.getStudentProfile()));
        } else {
            data.put("profile", null);
        }
    }
    
}
