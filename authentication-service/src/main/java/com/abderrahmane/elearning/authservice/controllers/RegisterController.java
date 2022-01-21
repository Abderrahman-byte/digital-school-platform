package com.abderrahmane.elearning.authservice.controllers;

import java.util.HashMap;
import java.util.Map;

import com.abderrahmane.elearning.authservice.models.Account;
import com.abderrahmane.elearning.authservice.repositories.AccountRepository;
import com.abderrahmane.elearning.authservice.utils.ErrorMessageResolver;
import com.abderrahmane.elearning.authservice.validators.RegisterFormValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/register")
public class RegisterController {
    @Autowired
    private RegisterFormValidator validator;

    @Autowired
    private ErrorMessageResolver messageResolver;

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> handlePost(@RequestBody Map<String, Object> body) {
        MapBindingResult errors = new MapBindingResult(body, "register");
        Map<String, Object> success = new HashMap<String, Object>();

        success.put("ok", true);
        validator.validate(body, errors);

        if (errors.hasErrors()) return messageResolver.constructErrorResponse(errors);

        String username = (String) body.get("username");
        String email = (String) body.get("email");
        String password = (String) body.get("password");
        String accountType = (String) body.get("accountType");

        try {
            Account account = accountRepository.insertAccount(username, email, password, accountType);
            System.out.println("Account id : " + account.getId());
        } catch (DataIntegrityViolationException ex) {
            String message = ex.getMessage();

            if (message.contains("account_email_key")) {
                errors.reject("duplicated", new Object[]{ "email" }, null);
            } else if (message.contains("account_username_key")) {
                errors.reject("duplicated", new Object[]{ "username" }, null);
            } else {
                errors.reject("defaultErrorMessage", "Someting went wrong");
                System.err.println("[" + ex.getClass().getName() + "] " + ex.getMessage());
            }

            return messageResolver.constructErrorResponse(errors);
        } catch (Exception ex) {
            errors.reject("defaultErrorMessage", "Someting went wrong");
            System.err.println("[" + ex.getClass().getName() + "] " + ex.getMessage());
            return messageResolver.constructErrorResponse(errors);
        }

        return success;
    }
}
