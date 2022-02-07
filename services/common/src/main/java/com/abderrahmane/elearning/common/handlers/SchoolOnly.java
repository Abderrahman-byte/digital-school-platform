package com.abderrahmane.elearning.common.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abderrahmane.elearning.common.models.Account;
import com.abderrahmane.elearning.common.models.AccountType;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.servlet.HandlerInterceptor;

public class SchoolOnly implements HandlerInterceptor {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Account account = (Account)request.getAttribute("account");
        Map<String, Object> errorMessage = new HashMap<>();

        if (account != null && account.getAccountType().equals(AccountType.SCHOOL) && account.getSchoolProfile() != null) return true;

        if (account == null || !account.getAccountType().equals(AccountType.SCHOOL)) {
            errorMessage.put("errors", List.of("school_account_only"));
        } else {
            errorMessage.put("errors", List.of("profile_incompleted"));
        }

        errorMessage.put("ok", false);
        response.setStatus(403);
        response.addHeader("Content-type", "application/json");
        response.getOutputStream().write(objectMapper.writeValueAsString(errorMessage).getBytes());

        return false;
    }
}
