package com.drainshawty.lab1.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.annotations.Filter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class UserCreationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().contains("/user/create") &&
                request.getMethod().equalsIgnoreCase("POST")) {

            String email = request.getParameter("email");

            if (email == null || email.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email is required.");
                return;
            }

            if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid email format.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
