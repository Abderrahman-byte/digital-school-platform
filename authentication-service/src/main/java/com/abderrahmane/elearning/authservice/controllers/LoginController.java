package com.abderrahmane.elearning.authservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.abderrahmane.elearning.authservice.converters.AccountMapConverter;
import com.abderrahmane.elearning.authservice.helpers.PasswordEncoder;
import com.abderrahmane.elearning.authservice.models.Account;
import com.abderrahmane.elearning.authservice.models.Session;
import com.abderrahmane.elearning.authservice.repositories.AccountRepository;
import com.abderrahmane.elearning.authservice.repositories.SessionRepository;
import com.abderrahmane.elearning.authservice.utils.ErrorMessageResolver;
import com.abderrahmane.elearning.authservice.validators.LoginFormValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    private LoginFormValidator validator;

    @Autowired
    private ErrorMessageResolver resolver;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountMapConverter accountMapConverter;

    @Value("${session.key:sid}")
    private String sessionKey;

    @PostMapping
    public Map<String, Object> handlePostRequest(@RequestBody Map<String, Object> form, HttpServletResponse httpResponse) {
        Map<String, Object> response = new HashMap<>();
        MapBindingResult errors = new MapBindingResult(form, "login");
        validator.validate(form, errors);

        if (errors.hasErrors())
            return resolver.constructErrorResponse(errors);

        Account account = this.authenticate((String) form.get("username"), (String) form.get("password"));

        if (account == null) {
            response.put("ok", false);
            response.put("errors", List.of("Username or password is incorrect"));
        } else if (!account.isActive()) {
            response.put("ok", false);
            response.put("errors", List.of("email_unverified"));
        } else {
            response.put("ok", true);
            response.put("data", accountMapConverter.convert(account));
            saveSession(httpResponse, account);
        }

        return response;
    }

    public Account authenticate(String username, String password) {
        Account account = accountRepository.selectByUsername(username);

        return account == null || !passwordEncoder.check(account.getPassword(), password) ? null : account;
    }

    public void saveSession (HttpServletResponse response, Account account) {
        Map<String, Object> sessionPayload = new HashMap<>();
        Session session;

        sessionPayload.put("account_id", account.getId());
        session = sessionRepository.save(sessionPayload);

        if (session != null) {
            Cookie cookie = new Cookie(sessionKey, session.getSid());
            cookie.setMaxAge((int)session.getMaxAge());    
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }
}
