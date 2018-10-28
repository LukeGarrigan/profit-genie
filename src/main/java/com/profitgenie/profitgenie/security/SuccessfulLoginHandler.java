package com.profitgenie.profitgenie.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class SuccessfulLoginHandler implements AuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(SuccessfulLoginHandler.class);

    private static final String ROLE = "ROLE_";

    private RedirectStrategy redirect = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String url = getTargetUrl(authentication);
        redirect.sendRedirect(request, response, url);
        clearAuthenticationAttributes(request);
    }

    private String getTargetUrl(Authentication authentication) {
        boolean isAdmin = false;
        boolean isMember = false;

        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            String authority = grantedAuthority.getAuthority();
            if (authority.equals(ROLE+SecurityConstants.SUPPORT)) {
                isAdmin = true;
            } else if (authority.equals(ROLE+SecurityConstants.MEMBER)) {
                isMember = true;
            }
        }

        String targetUrl = "/user-page.html";
        if (isAdmin) {
            targetUrl = "/admin-page.html";
        } else if (isMember) {
            targetUrl = "/members-page.html";
        }

        return targetUrl;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
