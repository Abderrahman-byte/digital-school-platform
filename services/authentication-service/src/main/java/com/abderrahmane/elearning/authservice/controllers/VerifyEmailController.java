package com.abderrahmane.elearning.authservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.common.models.Account;
import com.abderrahmane.elearning.common.repositories.AccountDAO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// TODO : May save user session after verification 

@RestController
@RequestMapping("/api/${api.version}/verify-email")
public class VerifyEmailController {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private AccountDAO accountRepository;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> handlePostRequest(@ModelAttribute("payload") Map<String, Claim> payload) {
        Map<String, Object> response = new HashMap<String, Object>();

        if (!this.checkPayload(payload)) {
            response.put("success", false);
            response.put("errors", List.of("invalid_token"));
            return response;
        }

        String id = payload.get("id").asString();
        String email = payload.get("email").asString();
        Account account = accountRepository.select(id);

        if (account != null && !account.isActive() && account.getEmail().equals(email)) {
            accountRepository.activateAccount(id);
            response.put("success", true);
        } else if (account != null && account.isActive()) {
            response.put("success", true);
            response.put("warn", List.of("already_active"));
        } else {
            response.put("success", false);
            response.put("errors", List.of("invalid_token"));
        }
        
        return response;
    }

    @ModelAttribute
    public void parseJWTPayload(@RequestParam(name = "q", required = false) String token, Model model) {
        if (token == null || token.length() <= 0) return;

        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        JWTVerifier verifier = JWT.require(algorithm).build();

        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            Map<String, Claim> payload = decodedJWT.getClaims();
            model.addAttribute("payload", payload);
        } catch (JWTVerificationException ex) {
            model.addAttribute("payload", null);
        }
    }

    public boolean checkPayload(Map<String, Claim> payload) {
        return payload != null && payload.containsKey("email") && payload.containsKey("id") && payload.containsKey("action") && payload.get("action").asString().equals("verify_email");
    }
}
