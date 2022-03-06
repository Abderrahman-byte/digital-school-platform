package com.abderrahmane.elearning.socialservice.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abderrahmane.elearning.common.models.Account;
import com.abderrahmane.elearning.common.models.AccountType;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.servlet.HandlerInterceptor;

public class TeachersOnly implements HandlerInterceptor {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Account account = (Account)request.getAttribute("account");
        Map<String, Object> responseBody = new HashMap<>();

        responseBody.put("ok", false);

        if (account != null && account.getAccountType().equals(AccountType.TEACHER) && account.getTeacherProfile() != null) return true;

        if (account == null || !account.getAccountType().equals(AccountType.TEACHER)) {
            responseBody.put("errors", List.of("unauthorized"));
        } else {
            responseBody.put("errors", List.of("uncompleted_profile"));
        }
        
        response.setStatus(401);
        response.setContentType("application/json");
        response.getOutputStream().write(objectMapper.writeValueAsBytes(responseBody));

        return false;
    }    
}
