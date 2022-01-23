package com.abderrahmane.elearning.authservice.handlers;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abderrahmane.elearning.authservice.models.Account;
import com.abderrahmane.elearning.authservice.models.Session;
import com.abderrahmane.elearning.authservice.repositories.AccountRepository;
import com.abderrahmane.elearning.authservice.repositories.SessionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationHandler implements HandlerInterceptor {
    @Value("${session.key:sid}")
    private String sessionKey;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie cookies[] = request.getCookies();

        request.setAttribute("authenticated", false);

        if (cookies == null) return true;

        for (Cookie cookie : cookies) {
            if (!cookie.getName().equals(sessionKey)) continue;

            Session session = sessionRepository.select(cookie.getValue());

            if (session == null) continue;

            Map<String, Object> payload = session.getPayload();

            if (payload == null || !payload.containsKey("account_id")) continue;

            String accountId = (String)payload.get("account_id");
            Account account = accountRepository.select(accountId);

            if (account != null) {
                request.setAttribute("account", account);
                request.setAttribute("session", session);
                request.setAttribute("authenticated", true);
                break;
            }
        }

        return true;
    }
}
