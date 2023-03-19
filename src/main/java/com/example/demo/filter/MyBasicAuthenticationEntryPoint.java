package com.example.demo.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MyBasicAuthenticationEntryPoint  {

    //Override
    //ublic void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
    //       throws IOException {
    //   response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    //   PrintWriter writer = response.getWriter();
    //   writer.println("HTTP Status 401 - " + authException.getMessage());
    //
    //
    //Override
    //ublic void afterPropertiesSet() {
    //   setRealmName("Baeldung");
    //   super.afterPropertiesSet();
    //
}