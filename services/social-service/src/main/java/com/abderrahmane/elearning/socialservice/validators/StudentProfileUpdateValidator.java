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
public class StudentProfileUpdateValidator extends GenericMapValidator {
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private Pattern dateFormatPattern = Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}$");

    public StudentProfileUpdateValidator () {
        this.addAllowedFields("firstName", "lastName", "dayOfBirth", "cityId");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void validate(Object target, Errors errors) {
        Map<String, Object> data = (Map<String, Object>)target;

        this.checkAllowedFields(data, errors);

        if (errors.hasErrors()) return ;

        this.checkStringValues(data, errors, List.of("firstName", "lastName", "dayOfBirth"));
        this.checkPositiveIntegers(data, errors, List.of("cityId"));

        String dayOfBirth = data.containsKey("dayOfBirth") ? (String)data.get("dayOfBirth") : null;

        if (data.containsKey("dayOfBirth") && dateFormatPattern.matcher(dayOfBirth).matches()) {
            try {
                Calendar dob = Calendar.getInstance();
                Date formatted = dateFormatter.parse(dayOfBirth);
                dob.setTime(formatted);
                data.put("day_of_birth", dob);
                data.remove("dayOfBirth");
            } catch (ParseException ex) {
                errors.rejectValue("dayOfBirth", "invalidValue");
            }
        } else if (data.containsKey("dayOfBirth")) {
            errors.rejectValue("dayOfBirth", "invalidValue");
        }

        if (errors.hasErrors()) return ;

        if (data.containsKey("firstName")) {
            data.put("first_name", data.get("firstName"));
            data.remove("firstName");
        }

        if (data.containsKey("lastName")) {
            data.put("last_name", data.get("lastName"));
            data.remove("lastName");
        }

        if (data.containsKey("cityId")) {
            data.put("location", data.get("cityId"));
            data.remove("cityId");
        }
    }
}
