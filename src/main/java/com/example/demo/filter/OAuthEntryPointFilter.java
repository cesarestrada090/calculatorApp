package com.example.demo.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;


public class OAuthEntryPointFilter extends AbstractPreAuthenticatedProcessingFilter {
    public static final String BEARER_MOCK_TOKEN = "Bearer b8920c1b-9f3d-4054-bef6-1da40ca8288e";
    Logger logger = LoggerFactory.getLogger(OAuthEntryPointFilter.class);

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return "";
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "";
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String header = ((HttpServletRequest) request).getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer")) {
            if(!BEARER_MOCK_TOKEN.equals(header)){
                response.setContentType("application/json");
                ((HttpServletResponse)response).sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid token");
                response.getWriter().write("{\"error\":\"invalid_token\",\"error_description\":\"Invalid Token\"}");
            }
            chain.doFilter(request, response);
        }
        chain.doFilter(request, response);
    }


}
