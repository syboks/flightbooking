package com.syboks.tich_flightbooking.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        System.out.println("The username " + username + " is logged in.");
        System.out.println("The user role is "+authentication.getAuthorities());
        boolean hasUserRole = authentication.getAuthorities().stream().anyMatch(r->r.getAuthority().equals("user"));
        boolean hasAdminRole = authentication.getAuthorities().stream().anyMatch(r->r.getAuthority().equals("admin"));

        if (hasUserRole || hasAdminRole) {
            response.sendRedirect("/customers");
        }
    }
}