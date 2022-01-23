package com.abderrahmane.elearning.authservice.converters;

import java.util.HashMap;
import java.util.Map;

import com.abderrahmane.elearning.authservice.models.Account;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AccountMapConverter implements Converter<Account, Map<String, Object>> {

    @Override
    public Map<String, Object> convert(Account source) {
        Map<String, Object> data = new HashMap<>();

        data.put("id", source.getId());
        data.put("username", source.getUsername());
        data.put("email", source.getEmail());
        data.put("accountType", source.getAccountType());
        data.put("isAdmin", source.isAdmin());

        return data;
    }
    
}
