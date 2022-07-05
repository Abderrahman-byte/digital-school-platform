package com.abderrahmane.elearning.common.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abderrahmane.elearning.common.models.Account;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class AuthenticatedOnly implements HandlerInterceptor {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Method on authencticated onlty : " + request.getMethod());
        
        if (request.getMethod().equals("OPTIONS")) return true;

        Account account = (Account)request.getAttribute("account");
        boolean authenticated = (Boolean)request.getAttribute("authenticated");
        Map<String, Object> message = new HashMap<>();
        String errorMessage;

        if (authenticated && account != null && account.isActive()) return true;

        errorMessage = account == null ? "authentication_required" : "unverified_email";

        message.put("success", false);
        message.put("errors", List.of(errorMessage));

        response.setStatus(403);
        response.setHeader("Content-Type", "application/json");
        response.getOutputStream().write(objectMapper.writeValueAsString(message).getBytes());

        return false;
    }
}
