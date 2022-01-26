package com.abderrahmane.elearning.socialservice.handlers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abderrahmane.elearning.socialservice.models.Account;
import com.abderrahmane.elearning.socialservice.models.Session;
import com.abderrahmane.elearning.socialservice.repositories.AccountDAO;
import com.abderrahmane.elearning.socialservice.repositories.SessionDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationHandler implements HandlerInterceptor {    
    @Value("${session.key:sid}")
    private String sessionCookieName;

    @Autowired
    private SessionDAO sessionDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie cookies [] = request.getCookies();

        request.setAttribute("authenticated", false);
        
        if (cookies == null) return true;
        
        for (Cookie cookie: cookies) {
            if (!cookie.getName().equals(sessionCookieName)) continue;

            Session session = sessionDAO.selectById(cookie.getValue());
            
            if (session == null || session.getPayload() == null || session.getPayload().get("account_id") == null) continue;

            String accountId = (String)session.getPayload().get("account_id");

            Account account = accountDAO.selectById(accountId);

            request.setAttribute("account", account);
            request.setAttribute("session", session);
            request.setAttribute("authenticated", true);
        }
        
        return true;
    }
}
