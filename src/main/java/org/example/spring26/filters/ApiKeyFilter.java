package org.example.spring26.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ApiKeyFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    public ApiKeyFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String apiKey = request.getHeader("X-Api-Key");
        if (apiKey != null) {
            try {
                ApiKeyAuthenticationToken authRequest = new ApiKeyAuthenticationToken(apiKey);
                //Ask AuthenticationManager to validate
                Authentication authResult = authenticationManager.authenticate(authRequest);
                //If valid, add to SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authResult);
            } catch (AuthenticationException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid Api-Key");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
