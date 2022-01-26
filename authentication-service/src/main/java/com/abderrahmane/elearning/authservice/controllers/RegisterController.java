package com.abderrahmane.elearning.authservice.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.abderrahmane.elearning.authservice.helpers.MailService;
import com.abderrahmane.elearning.authservice.models.Account;
import com.abderrahmane.elearning.authservice.repositories.AccountRepository;
import com.abderrahmane.elearning.authservice.utils.ErrorMessageResolver;
import com.abderrahmane.elearning.authservice.validators.RegisterFormValidator;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/register")
public class RegisterController {
    @Autowired
    private RegisterFormValidator validator;

    @Autowired
    private ErrorMessageResolver messageResolver;

    @Autowired
    private AccountRepository accountRepository;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private MailService mailService;

    @Autowired
    private Environment environment;

    // FIXME : Send verification email can be asynchronous

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> handlePost(@RequestBody Map<String, Object> body, HttpServletRequest request) {
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
            sendVerificationMessage(request, email, account.getId());
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

    private void sendVerificationMessage (HttpServletRequest request, String email, String id) {
        String frontendVerificationUrl = environment.getProperty("frontend.verifyEmail.url");
        UriComponentsBuilder uriBuilder = frontendVerificationUrl != null
                ? UriComponentsBuilder.fromHttpUrl(frontendVerificationUrl)
                : ServletUriComponentsBuilder.fromRequestUri(request).replacePath("/api/verify-email");
        UriComponents uriComponents = uriBuilder.queryParam("q", "{token}").build();


        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        Map<String, Object> payload = new HashMap<>();
        String token, verificationUrl; 
        
        payload.put("email", email);
        payload.put("id", id);
        payload.put("action", "verify_email");

        token = JWT.create().withPayload(payload).sign(algorithm);
        verificationUrl = uriComponents.expand(token).encode().toUriString();

        mailService.sendMail(email, "Email Verification", "Verify your email through : \n" + verificationUrl);
    }
}
