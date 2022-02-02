package com.abderrahmane.elearning.socialservice.validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.abderrahmane.elearning.common.validators.GenericMapValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class StudentProfileCreationValidator extends GenericMapValidator {
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    Pattern dateFormatPattern = Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}$");

    public StudentProfileCreationValidator () {
        this.addRequiredFields("firstName", "lastName", "dayOfBirth", "cityId");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void validate(Object target, Errors errors) {
        Map<String, Object> data = (Map<String, Object>)target;
        
        this.checkAllowedFields(data, errors);
        this.checkRequiredFields(data, errors);

        if (errors.hasErrors()) return;

        List.of("firstName", "lastName", "dateOfBirth").forEach(field -> {
            if (data.containsKey(field) && !data.get(field).getClass().equals(String.class)) {
                errors.rejectValue(field, "invalidType");
            } else if (data.containsKey(field) && ((String)data.get(field)).length() <= 0) {
                errors.rejectValue(field, "invalidValue");
            }
        });

        if (!data.get("cityId").getClass().equals(Integer.class)) {
            errors.rejectValue("cityId", "invalidType");
        } else if ((Integer)data.get("cityId") <= 0) {
            errors.rejectValue("cityId", "invalidValue");
        }
        
        String dayOfBirth = (String)data.get("dayOfBirth");

        if (dateFormatPattern.matcher(dayOfBirth).matches()) {
            try {
                Calendar dob = Calendar.getInstance();
                Date formatted = dateFormatter.parse(dayOfBirth);
                dob.setTime(formatted);
                data.put("dayOfBirth", dob);
            } catch (ParseException ex) {
                errors.rejectValue("dayOfBirth", "invalidValue");
            }
        } else {
            errors.rejectValue("dayOfBirth", "invalidValue");
        }

    }
    
}
